package com.tmlk.service;

import com.tmlk.po.PartyExt;
import com.tmlk.po.PartyUserExt;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPartyServiceExt extends IPartyService{

    PartyUserExt launch(PartyExt partyExt,HttpServletRequest request) throws Exception;

    void edit(PartyExt partyExt);

    List<PartyUserExt> getPartyUsers(String partyId);

    boolean existParty(String partyCode);

}
