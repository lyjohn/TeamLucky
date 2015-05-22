package com.tmlk.service;

import com.tmlk.po.PartyUserExt;

import java.util.List;

public interface IPartyServiceExt extends IPartyService{

    List<PartyUserExt> getPartyUsers(String partyId);

    boolean existParty(String partyCode);
}
