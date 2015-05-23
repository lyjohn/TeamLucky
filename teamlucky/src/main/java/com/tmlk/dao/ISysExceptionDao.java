package com.tmlk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.tmlk.framework.util.Pagination;
import com.tmlk.po.SysExceptionExt;

@Component("sysExceptionDao")
public interface ISysExceptionDao {

	int create(SysExceptionExt sysException);
	
	void update(SysExceptionExt sysException);
	
	SysExceptionExt load(@Param("id") Long  id);
	
	void delete(@Param("id") Long  id);

	List<SysExceptionExt> criteriaQuery(@Param("mqlList") List<String> mqlList, @Param("mortList") List<String> mortList, @Param("p") Map<String, Object> p, @Param("pp") Pagination pp);
	
	int criteriaCount(@Param("mqlList") List<String> mqlList, @Param("p") Map<String, Object> p);

}
