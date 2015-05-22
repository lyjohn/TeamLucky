package com.tmlk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.tmlk.framework.util.Pagination;
import com.tmlk.po.PartyExt;

@Component("partyDao")
public interface IPartyDao {

	int create(PartyExt party);
	
	void update(PartyExt party);
	
	PartyExt load(@Param("id") Long  id);
	
	void delete(@Param("id") Long  id);

	List<PartyExt> criteriaQuery(@Param("mqlList") List<String> mqlList, @Param("mortList") List<String> mortList, @Param("p") Map<String, Object> p, @Param("pp") Pagination pp);
	
	int criteriaCount(@Param("mqlList") List<String> mqlList, @Param("p") Map<String, Object> p);

	PartyExt loadByCode(@Param("code") String code);
}
