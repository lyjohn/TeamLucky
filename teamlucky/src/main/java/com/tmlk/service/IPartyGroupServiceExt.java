package com.tmlk.service;

import com.tmlk.po.PartyExt;
import com.tmlk.po.PartyGroupExt;

public interface IPartyGroupServiceExt extends IPartyGroupService{

    PartyGroupExt build(PartyGroupExt partyGroupExt);

    public PartyGroupExt updateGroup(PartyGroupExt partyGroupExt);
}
