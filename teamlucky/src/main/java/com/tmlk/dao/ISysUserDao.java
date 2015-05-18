package com.tmlk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.tmlk.framework.util.Pagination;
import com.tmlk.po.SysUserExt;

@Component("sysUserDao")
public interface ISysUserDao {

	int create(SysUserExt sysUser);
	
	void update(SysUserExt sysUser);
	
	SysUserExt load(@Param("id") String  id);
	
	void delete(@Param("id") String  id);

	List<SysUserExt> criteriaQuery(@Param("mqlList") List<String> mqlList, @Param("mortList") List<String> mortList, @Param("p") Map<String, Object> p, @Param("pp") Pagination pp);
	
	int criteriaCount(@Param("mqlList") List<String> mqlList, @Param("p") Map<String, Object> p);

}
