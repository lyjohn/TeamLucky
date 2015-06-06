package com.tmlk.service;

import com.tmlk.po.PartyExt;
import com.tmlk.po.PartyUserExt;
import com.tmlk.po.SysUserExt;
import org.apache.poi.ss.usermodel.Sheet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface IPartyServiceExt extends IPartyService{

    PartyUserExt launch(PartyExt partyExt,HttpServletRequest request) throws Exception;

    /**
     * 系统用户加入活动
     * @param sysUser 系统用户
     * @param party 活动
     * @return
     */
    PartyUserExt join(SysUserExt sysUser,PartyExt party);

    void edit(PartyExt partyExt);

    boolean existParty(String partyCode);

    PartyExt updateParty(PartyExt partyExt,int updateType);

    List<PartyUserExt> importMember(Sheet sheet,Long partyId,HttpServletRequest request);

    /**
     * 进入活动 个人的活跃度+1点 活动活跃度+1 小组活跃度+1
     * @param partyUserExt 用来传UserID
     * @param partyExt 活动Id
     */
    void loginParty(PartyUserExt partyUserExt,PartyExt partyExt);
}
