package com.tmlk.service.impl;

import com.tmlk.framework.mybatis.EqCondition;
import com.tmlk.framework.mybatis.ICondition;
import com.tmlk.framework.mybatis.Order;
import com.tmlk.po.PartyExt;
import com.tmlk.po.PartyUserExt;
import com.tmlk.service.IPartyUserService;
import com.tmlk.service.IPartyUserServiceExt;
import org.apache.log4j.Logger;

import com.tmlk.service.IPartyServiceExt;

import java.util.ArrayList;
import java.util.List;

public class PartyServiceExt extends PartyService implements IPartyServiceExt {
	
	private static final Logger logger = Logger.getLogger(PartyServiceExt.class);

	private IPartyUserServiceExt partyUserService;

	public IPartyUserServiceExt getPartyUserService() {
		return partyUserService;
	}

	public void setPartyUserService(IPartyUserServiceExt partyUserService) {
		this.partyUserService = partyUserService;
	}

	@Override
	public List<PartyUserExt> getPartyUsers(String partyId) {

		List<ICondition> conditions = new ArrayList<ICondition>();

		conditions.add(new EqCondition("partyId",partyId));

		List<Order> orders = new ArrayList<Order>();

		orders.add(Order.asc("userName"));

		List<PartyUserExt> partyUsers = partyUserService.criteriaQuery(conditions, orders);

		return partyUsers;
	}

	@Override
	public boolean existParty(String partyCode) {

		PartyExt partyExt = getPartyDao().loadByCode(partyCode);

		if(partyExt == null)
			return  false;
		else
			return true;
	}
}