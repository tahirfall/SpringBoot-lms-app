package com.esmt.misi2.lms.model.service;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmt.misi2.lms.model.dao.IUserDao;
import com.esmt.misi2.lms.model.entity.Role;
import com.esmt.misi2.lms.model.entity.UserLogin;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{

	@Autowired
	private IUserDao userDao;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
        UserLogin userLogin = userDao.findByUsername(username);
        
        if(userLogin == null) {
        	logger.error("Error! user '" + username + "' does not exist!!!");
        	throw new UsernameNotFoundException("Username: " + username + " doesn't exist!!!");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        for(Role role: userLogin.getRoles()) {
        	logger.info("Role: ".concat(role.getAuthority()));
        	authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        
        if(authorities.isEmpty()) {
        	logger.error("Error!: user '" + username + "' he does not have assigned roles!");
        	throw new UsernameNotFoundException("Error!: user '" + username + "' he does not have assigned roles!");
        }
        
		return new User(userLogin.getUsername(), userLogin.getPassword(), userLogin.getEnabled(), true, true, true, authorities);
	}
	
	
}
