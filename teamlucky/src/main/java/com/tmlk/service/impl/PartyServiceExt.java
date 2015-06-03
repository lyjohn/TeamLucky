package com.tmlk.service.impl;

import com.tmlk.aop.SysServiceLog;
import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.framework.util.FormatUtils;
import com.tmlk.po.*;
import com.tmlk.service.*;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
			partyUserExt.setHotCount(0);
			partyUserExt.setPartyId(party.getId());
			partyUserExt.setGroupId(0L);//没有分组就是0
			partyUserExt.setUserStatus(16);
			partyUserExt.setBirthDay(author.getBirthDay());
			partyUserExt.setEmail(author.getEmail());
			partyUserExt.setLastLoginIP(author.getLastLoginIP());
			partyUserExt.setLastLoginTime(author.getLastLoginTime());
			partyUserExt.setLoginName(party.getPartyCode()+"_"+author.getUserName());
			partyUserExt.setLoginPwd(author.getLoginPwd());
			partyUserExt.setQq(author.getQq());
			partyUserExt.setRegisterIP(FormatUtils.getIpAddress(request));
			partyUserExt.setRegisterTime(party.getCreateTime());
			partyUserExt.setSex(author.getSex());
			partyUserExt.setTel(author.getTel());
			partyUserExt.setUserName(author.getLoginName());
			partyUserExt.setWeiXin(author.getWeiXin());
			partyUserExt.setUserAvatar(author.getUserAvatar());

			if (!partyExt.getIsGroup()) {//创建同名小组
				PartyGroupExt partyGroupExt = new PartyGroupExt();

				partyGroupExt.setCreateBy(party.getCreateBy());
				partyGroupExt.setHotCount(0);
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
}