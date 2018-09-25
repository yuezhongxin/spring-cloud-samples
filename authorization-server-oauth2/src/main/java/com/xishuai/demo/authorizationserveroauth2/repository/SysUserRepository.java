package com.xishuai.demo.authorizationserveroauth2.repository;

import com.xishuai.demo.authorizationserveroauth2.domain.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SysUserRepository {

    @Select("select * from sys_user where user_name='${user_name}' ")
    @Results({
            @Result(property = "username", column = "user_name"),
            @Result(property = "roles", column = "id", many = @Many(select="com.xishuai.demo.authorizationserveroauth2.repository.SysRoleRepository.getByUserId"))
    })
    SysUser findByUsername(@Param("user_name") String username);

    @Select("select * from sys_user where user_name='${phone}' or phone='${phone}' ")
    @Results({
            @Result(property = "username", column = "user_name"),
            @Result(property = "roles", column = "id", many = @Many(select="com.xishuai.demo.authorizationserveroauth2.repository.SysRoleRepository.getByUserId"))
    })
    SysUser findByPhone(@Param("phone") String phone);

    @Select("select * from sys_user where id in(select sys_user_id from sys_user_role where sys_role_id=#{roleId})")
    @Results({
            @Result(property = "username", column = "user_name"),
            @Result(property = "roles", column = "id", many = @Many(select="com.xishuai.demo.authorizationserveroauth2.repository.SysRoleRepository.getByUserId"))
    })
    List<SysUser> getByRoleId(Integer roleId);

    @Select("select count(id) from sys_user where phone='${phone}' ")
    Boolean containsByPhone(@Param("phone") String phone);

}
