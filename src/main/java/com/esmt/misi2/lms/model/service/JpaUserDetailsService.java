package com.esmt.misi2.lms.model.service;

import java.util.ArrayList;
import java.util.List;

import com.esmt.misi2.lms.model.dao.IUsersDao;
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

import com.esmt.misi2.lms.model.entity.UserModel;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private IUsersDao userDao;

	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserModel user = userDao.findByUsername(username);

		if (user == null) {
			logger.error("Error! user '" + username + "' does not exist!!!");
			throw new UsernameNotFoundException("Username: " + username + " doesn't exist!!!");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();

		// Ajoutez le rôle de l'utilisateur à la liste des autorisations
		authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

		if (authorities.isEmpty()) {
			logger.error("Error!: user '" + username + "' does not have assigned roles!");
			throw new UsernameNotFoundException("Error!: user '" + username + "' does not have assigned roles!");
		}

		return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}
}
