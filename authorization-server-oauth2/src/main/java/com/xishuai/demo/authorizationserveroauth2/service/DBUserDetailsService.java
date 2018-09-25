package com.xishuai.demo.authorizationserveroauth2.service;

import com.xishuai.demo.authorizationserveroauth2.repository.SysUserRepository;
import com.xishuai.demo.authorizationserveroauth2.repository.UserDetailsImpl;
import com.xishuai.demo.authorizationserveroauth2.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//自定义UserDetailsService，用户信息从数据库中读取验证
@Service
public class DBUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserMapper;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserMapper.findByPhone(username);
        if (user == null){
            throw new BadCredentialsException("手机号不存在");
        }

        return new UserDetailsImpl(user);

        /*List<SysRole> roleList = user.getRoles();
        String[] roles = new String[roleList.size()];

        for (int i = 0; i < roleList.size(); i++) {
            roles[i] = roleList.get(i).getName();
        }*/

        //return User.withUsername(username).password(user.getPassword()).roles(roles).build();
        //return new User(username, passwordEncoder.encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }

}
