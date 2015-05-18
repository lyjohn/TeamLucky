package com.tmlk.dao;

import com.tmlk.framework.util.Pagination;
import com.tmlk.po.UserExt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by LaiGuoqiang on 15/5/17.
 */

@Component("userDao")
public interface IUserDao {
    int create(UserExt sysUser);

    void update(UserExt sysUser);

    UserExt load(@Param("id") Long id);

    void delete(@Param("id") Long id);

    List<UserExt> criteriaQuery(@Param("mqlList") List<String> mqlList, @Param("mortList") List<String> mortList, @Param("p") Map<String, Object> p, @Param("pp") Pagination pp);

    int criteriaCount(@Param("mqlList") List<String> mqlList, @Param("p") Map<String, Object> p);


}
