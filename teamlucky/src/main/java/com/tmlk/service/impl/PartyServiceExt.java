package com.tmlk.service.impl;

import com.tmlk.aop.SysServiceLog;
import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.Constants;
import com.tmlk.framework.util.ExcelUtils;
import com.tmlk.framework.util.FormatUtils;
import com.tmlk.framework.util.MD5Util;
import com.tmlk.po.*;
import com.tmlk.service.*;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.*;

public class PartyServiceExt extends PartyService implements IPartyServiceExt {
	
	private static final Logger logger = Logger.getLogger(PartyServiceExt.class);

	private ISysUserServiceExt sysUserService;

	public ISysUserServiceExt getSysUserService() {
		return sysUserService;
	}

	public void setSysUserService(ISysUserServiceExt sysUserService) {
		this.sysUserService = sysUserService;
	}

	private ISysPartyUserLinkServiceExt sysPartyUserLinkService;

	public ISysPartyUserLinkServiceExt getSysPartyUserLinkService() {
		return sysPartyUserLinkService;
	}

	public void setSysPartyUserLinkService(ISysPartyUserLinkServiceExt sysPartyUserLinkService) {
		this.sysPartyUserLinkService = sysPartyUserLinkService;
	}

	private IPartyUserServiceExt partyUserService;

	public IPartyUserServiceExt getPartyUserService() {
		return partyUserService;
	}

	public void setPartyUserService(IPartyUserServiceExt partyUserService) {
		this.partyUserService = partyUserService;
	}

	private IPartyGroupServiceExt partyGroupService;

	public IPartyGroupServiceExt getPartyGroupService() {
		return partyGroupService;
	}

	public void setPartyGroupService(IPartyGroupServiceExt partyGroupService) {
		this.partyGroupService = partyGroupService;
	}

	/**
	 * 创建活动，并把新建的活动用户返回去
	 * @param partyExt 填写的表单内容
	 * @param request HttpServletRequest对象
	 * @return PartyUserExt
	 * @throws Exception
	 */
	@Override
	@SysServiceLog(description = "创建活动", code = 201)
	public PartyUserExt launch(PartyExt partyExt,HttpServletRequest request) throws Exception {
		SysUserExt author = sysUserService.load(partyExt.getCreateBy());
		if(author == null)
			throw new Exception("只有系统注册用户 才能创建活动");

		//不分组 我们将自动创建一个同名小组来保存所有用户
		if(!partyExt.getIsGroup()){
			partyExt.setBuildEndTime(null);
			partyExt.setMemberNumMax(0);
			partyExt.setMemberNumMin(0);
			partyExt.setIsCustomBuild(null);
		}

		partyExt.setCreateTime(new Date());
		partyExt.setHotCount(0);
		partyExt.setMemberCount(1);
		partyExt.setPartyStatus(1);

		PartyExt party =  this.create(partyExt);
		if(party != null) {//创建成功
			//把自己加入这个活动，即创建一个关联到自己的活动用户
			PartyUserExt partyUserExt = new PartyUserExt();
			partyUserExt.setHotCount(1);
			partyUserExt.setPartyId(party.getId());
			partyUserExt.setGroupId(0L);//没有分组就是0
			partyUserExt.setUserStatus(16);
			partyUserExt.setBirthDay(author.getBirthDay());
			partyUserExt.setEmail(author.getEmail());
			partyUserExt.setLastLoginIP(author.getLastLoginIP());
			partyUserExt.setLastLoginTime(author.getLastLoginTime());
			partyUserExt.setLoginName(party.getPartyCode()+"_"+author.getLoginName());
			partyUserExt.setLoginPwd(author.getLoginPwd());
			partyUserExt.setQq(author.getQq());
			partyUserExt.setRegisterIP(FormatUtils.getIpAddress(request));
			partyUserExt.setRegisterTime(party.getCreateTime());
			partyUserExt.setSex(author.getSex());
			partyUserExt.setTel(author.getTel());
			partyUserExt.setUserName(author.getUserName());
			partyUserExt.setWeiXin(author.getWeiXin());
			partyUserExt.setUserAvatar(author.getUserAvatar());

			if (!partyExt.getIsGroup()) {//创建同名小组
				PartyGroupExt partyGroupExt = new PartyGroupExt();

				partyGroupExt.setCreateBy(party.getCreateBy());
				partyGroupExt.setHotCount(1);
				partyGroupExt.setCreateTime(party.getCreateTime());
				if (!FormatUtils.isEmpty(party.getPartyCover())) {
					partyGroupExt.setGroupCover(party.getPartyCover());
				}
				partyGroupExt.setGroupName(party.getPartyName());
				partyGroupExt.setGroupRemark(party.getPartyRemark());

				partyGroupExt.setGroupStatus(1);//1:有效 2:不能加入
				partyGroupExt.setIsCustomJoin(party.getIsPublic());//能否自由加入
				partyGroupExt.setPartyId(party.getId());
				partyGroupExt.setIsSourcePublic(true);//小组内的资源公开
				partyGroupExt.setMemberCount(1);

				PartyGroupExt partyGroup = partyGroupService.build(partyGroupExt);

				partyUserExt.setGroupId(partyGroup.getId());
			}

			PartyUserExt partyUser = partyUserService.register(partyUserExt);

			SysPartyUserLinkExt sysPartyUserLinkExt = new SysPartyUserLinkExt();
			sysPartyUserLinkExt.setPartyId(party.getId());
			sysPartyUserLinkExt.setPartyUserId(partyUser.getId());
			sysPartyUserLinkExt.setSysUserId(author.getId());
			sysPartyUserLinkExt.setJoinTime(new Date());
			sysPartyUserLinkService.create(sysPartyUserLinkExt);

			return partyUser;
		}

		return null;
	}

	public PartyUserExt join(SysUserExt sysUser,PartyExt party){
		PartyUserExt partyUserExt = new PartyUserExt();
		partyUserExt.setHotCount(1);
		partyUserExt.setPartyId(party.getId());
		partyUserExt.setGroupId(0L);//没有分组就是0
		partyUserExt.setUserStatus(2);
		partyUserExt.setBirthDay(sysUser.getBirthDay());
		partyUserExt.setEmail(sysUser.getEmail());
		partyUserExt.setLastLoginIP(sysUser.getLastLoginIP());
		partyUserExt.setLastLoginTime(sysUser.getLastLoginTime());
		partyUserExt.setLoginName(party.getPartyCode()+"_"+sysUser.getLoginName());
		partyUserExt.setLoginPwd(sysUser.getLoginPwd());
		partyUserExt.setQq(sysUser.getQq());
		partyUserExt.setRegisterIP(sysUser.getLastLoginIP());
		partyUserExt.setRegisterTime(party.getCreateTime());
		partyUserExt.setSex(sysUser.getSex());
		partyUserExt.setTel(sysUser.getTel());
		partyUserExt.setUserName(sysUser.getUserName());
		partyUserExt.setWeiXin(sysUser.getWeiXin());
		partyUserExt.setUserAvatar(sysUser.getUserAvatar());
		PartyUserExt partyUser = partyUserService.register(partyUserExt);

		SysPartyUserLinkExt sysPartyUserLinkExt = new SysPartyUserLinkExt();
		sysPartyUserLinkExt.setPartyId(party.getId());
		sysPartyUserLinkExt.setPartyUserId(partyUser.getId());
		sysPartyUserLinkExt.setSysUserId(sysUser.getId());
		sysPartyUserLinkExt.setJoinTime(new Date());
		sysPartyUserLinkService.create(sysPartyUserLinkExt);

		return partyUser;
	}

	@Override
	@SysServiceLog(description = "编辑活动基本信息",code = 203)
	public void edit(PartyExt partyExt) {
		this.update(partyExt);
	}

	@Override
	public boolean existParty(String partyCode) {

		PartyExt partyExt = getPartyDao().loadByCode(partyCode);

		if(partyExt == null)
			return  false;
		else
			return true;
	}

	@Override
	@SysServiceLog(description = "编辑活动基本信息",code = 203)
	public PartyExt updateParty(PartyExt partyExt, int updateType) {
		PartyExt partyExtPer = this.load(partyExt.getId());
		if (partyExtPer == null)
			return null;

		if(updateType == 1){//只编辑了 名称 描述  是否公共
			partyExtPer.setPartyName(partyExt.getPartyName());
			partyExtPer.setIsPublic(partyExt.getIsPublic());
			partyExtPer.setPartyRemark(partyExt.getPartyRemark());
		}else if(updateType == 2){//编辑了分组配置  不能修改是否分组这个属性
			partyExtPer.setMemberNumMin(partyExt.getMemberNumMin());
			partyExtPer.setMemberNumMax(partyExt.getMemberNumMax());
			partyExtPer.setIsCustomBuild(partyExt.getIsCustomBuild());
			partyExtPer.setBuildEndTime(partyExt.getBuildEndTime());
		}else
			return partyExtPer;

		this.update(partyExtPer);

		return partyExtPer;
	}

	@Override
	@SysServiceLog(description = "导入活动成员",code = 204)
	public List<PartyUserExt> importMember(Sheet sheet, Long partyId,HttpServletRequest request) {
		List<PartyUserExt> result = new ArrayList<PartyUserExt>();

		Map<String,List<Object>> map  = new HashMap<String, List<Object>>(30);
		//第1行标题略过
		for(int i=1;i<=ExcelUtils.getSheetRowNum(sheet);i++) {
			List<Object> originUser = new ArrayList<Object>(20) ;

			for(int j=0;j< ExcelUtils.getSheetColCount(sheet, 0);j++) {
				originUser.add(ExcelUtils.getCellValue(sheet, i, j, Constants.DATA_TYPE_STRING));
			}
			//第1列是用户登录名
			map.put(originUser.get(0).toString(), originUser);
		}

		List<String> key = new ArrayList<String>(map.size());
		key.addAll(map.keySet());
		Collections.sort(key);

		String registerIp = request.getRemoteAddr();
		Date registerTime = new Date();

		PartyExt partyExt = this.load(partyId);
		String code = partyExt.getPartyCode();
		for(String t:key) {
			List<Object> originUser = map.get(t);

			String loginName = code+"_"+originUser.get(0).toString();
			//查询是否有该用户，有则不导入，之所以不覆盖，是因为成员自己修改了内容，管理员不好直接覆盖吧...
			PartyUserExt partyUserExt = partyUserService.findUserByName(loginName);
			if(partyUserExt != null)
				continue;

			partyUserExt = new PartyUserExt();
			partyUserExt.setPartyId(partyId);
			partyUserExt.setGroupId(0L);
			partyUserExt.setHotCount(0);
			partyUserExt.setRegisterIP(registerIp);
			partyUserExt.setRegisterTime(registerTime);
			partyUserExt.setUserStatus(2);

			//帐号前加识别码
			partyUserExt.setLoginName(loginName);
			//密码
			if(originUser.get(1) == null){
				partyUserExt.setLoginPwd(MD5Util.MD5("123456"));
			}else{
				partyUserExt.setLoginPwd(MD5Util.MD5(originUser.get(1).toString()));
			}
			//昵称
			if(originUser.get(2) == null){
				partyUserExt.setUserName(originUser.get(0).toString());
			}else{
				partyUserExt.setUserName(originUser.get(2).toString());
			}
			//性别
			if(originUser.get(3) != null){
				if(originUser.get(3).toString().equals("男"))
					partyUserExt.setSex("男");
				else if(originUser.get(3).toString().equals("女"))
					partyUserExt.setSex("女");
			}
			//生日
			if(originUser.get(4) != null){
				Date birthDay = FormatUtils.getDate(originUser.get(4).toString(), "yyyy-MM-dd");
				if(birthDay!=null)
					partyUserExt.setBirthDay(birthDay);
				else
					birthDay = FormatUtils.getDate(originUser.get(4).toString(),"yyyy/M/d");
			}
			//描述
			partyUserExt.setUserRemark(originUser.get(5) == null ? "" : originUser.get(5).toString());

			//手机号码
			if(originUser.get(6)!=null){
				if(originUser.get(6).toString().length() < 12)
					partyUserExt.setTel(originUser.get(6).toString());
			}

			//邮箱
			partyUserExt.setEmail(originUser.get(7) == null ? "" : originUser.get(7).toString());

			//QQ
			if(originUser.get(8)!=null){
				if(originUser.get(8).toString().length() < 12)
					partyUserExt.setQq(originUser.get(8).toString());
			}

			//微信
			if(originUser.get(9)!=null){
				if(originUser.get(9).toString().length() < 21)
					partyUserExt.setWeiXin(originUser.get(9).toString());
			}

			try {
				partyUserService.register(partyUserExt);
				result.add(partyUserExt);
			}catch (Exception ex){
				ex.getStackTrace();
			}
		}

		return result;
	}

	@Override
	@SysServiceLog(description = "用户进入访问活动",code = 205)
	public void loginParty(PartyUserExt partyUserExt, PartyExt partyExt) {

		Date dt = partyUserExt.getLastLoginTime();

		//获取系统当前时间
		Date time=new Date();
		String s;
		if(dt==null){
			s="";
		}else {
			s = DateFormat.getDateInstance().format(dt);
		}
		String t= DateFormat.getDateInstance().format(time);
		//将当前系统时间和用户最后登录时间进行格式化，判断是否为同一天
		if(!s.equals(t)){
			partyExt.setHotCount(partyExt.getHotCount()+1);
			this.update(partyExt);

			if(partyUserExt.getGroupId()!=0){
				PartyGroupExt partyGroupExt = partyGroupService.load(partyUserExt.getGroupId());
				if(partyGroupExt!=null){
					partyGroupExt.setHotCount(partyGroupExt.getHotCount()+1);
					partyGroupService.update(partyGroupExt);
				}
			}

			partyUserExt.setHotCount(partyUserExt.getHotCount()+1);
			partyUserExt.setLastLoginTime(time);
			partyUserService.update(partyUserExt);
		}
	}
}