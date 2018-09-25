package com.xishuai.demo.authorizationserveroauth2.repository;

import com.xishuai.demo.authorizationserveroauth2.domain.SysRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SysRoleRepository {

    @Select("select * from sys_role where id in(select sys_role_id from sys_user_role where sys_user_id=#{userId})")
    @Results({
            @Result(property = "users", column = "id", many = @Many(select="com.xishuai.demo.authorizationserveroauth2.repository.SysUserRepository.getByRoleId"))
    })
    List<SysRole> getByUserId(Integer userId);

}
