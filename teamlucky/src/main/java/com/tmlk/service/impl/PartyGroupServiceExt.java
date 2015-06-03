package com.tmlk.service.impl;

import com.tmlk.aop.SysServiceLog;
import com.tmlk.po.PartyGroupExt;
import org.apache.log4j.Logger;

import com.tmlk.service.IPartyGroupServiceExt;

import java.util.UUID;

public class PartyGroupServiceExt extends PartyGroupService implements IPartyGroupServiceExt {
	
	private static final Logger logger = Logger.getLogger(PartyGroupServiceExt.class);

	@Override
	@SysServiceLog(description = "创建活动小组", code = 301)
	public PartyGroupExt build(PartyGroupExt partyGroupExt) {

		PartyGroupExt partyGroup = this.create(partyGroupExt);

		return partyGroup;
	}

	@Override
	@SysServiceLog(description = "编辑小组基本信息", code = 303)
	public PartyGroupExt updateGroup(PartyGroupExt partyGroupExt) {
		PartyGroupExt partyGroupExtPer = this.load(partyGroupExt.getId());
		if (partyGroupExtPer == null)
			return null;

		partyGroupExtPer.setGroupName(partyGroupExt.getGroupName());
		partyGroupExtPer.setGroupRemark(partyGroupExt.getGroupRemark());
		partyGroupExtPer.setIsCustomJoin(partyGroupExt.getIsCustomJoin());
		partyGroupExtPer.setIsSourcePublic(partyGroupExt.getIsSourcePublic());

		this.update(partyGroupExtPer);

		return partyGroupExtPer;

	}
}