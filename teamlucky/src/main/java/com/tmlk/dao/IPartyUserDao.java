package com.tmlk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.tmlk.framework.util.Pagination;
import com.tmlk.po.PartyUserExt;

@Component("partyUserDao")
public interface IPartyUserDao {

	int create(PartyUserExt partyUser);
	
	void update(PartyUserExt partyUser);
	
	PartyUserExt load(@Param("id") String  id);
	
	void delete(@Param("id") String  id);

	List<PartyUserExt> criteriaQuery(@Param("mqlList") List<String> mqlList, @Param("mortList") List<String> mortList, @Param("p") Map<String, Object> p, @Param("pp") Pagination pp);
	
	int criteriaCount(@Param("mqlList") List<String> mqlList, @Param("p") Map<String, Object> p);

}
