package com.tmlk.service;

import com.tmlk.po.PartyExt;
import com.tmlk.po.PartyUserExt;
import org.apache.poi.ss.usermodel.Sheet;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IPartyServiceExt extends IPartyService{

    PartyUserExt launch(PartyExt partyExt,HttpServletRequest request) throws Exception;

    void edit(PartyExt partyExt);

    boolean existParty(String partyCode);

    PartyExt updateParty(PartyExt partyExt,int updateType);

    List<PartyUserExt> importMember(Sheet sheet,Long partyId,HttpServletRequest request);
}
