package com.co.andresfot.libreria;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.co.andresfot.libreria.auth.handler.LoginSuccesHandler;
import com.co.andresfot.libreria.model.service.JpaUserDetailsService;

@Configuration
public class SpringSecurityConfig {
	
	@Autowired
	private LoginSuccesHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JpaUserDetailsService userDetailsServiceJpa;
	
	@Bean //autorización
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().requestMatchers("/libros/lista-libros", "/css/**", "/js/**", "/images/**")
			.permitAll()
		.requestMatchers("/libros/crear-libro").hasAnyRole("ADMIN")
		.requestMatchers("/libros/editar-libro/**").hasAnyRole("ADMIN")
		.requestMatchers("/libros/eliminar-libro/**").hasAnyRole("ADMIN")
		.requestMatchers("/autores/**").hasAnyRole("ADMIN")
		.requestMatchers("/prestamos/**").hasAnyRole("ADMIN")
		.requestMatchers("/usuarios/**").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and()
			.formLogin()
				.successHandler(successHandler)
				.loginPage("/login")
			.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
        	
        return http.build();
    }
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception{
		build.userDetailsService(userDetailsServiceJpa).passwordEncoder(passwordEncoder);
	}

	/*
	@Bean //configuration global
	public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
		
		List<UserDetails> userDetailsList = new ArrayList<>();
		userDetailsList.add(User.withUsername("admin").password(this.passwordEncoder.encode("password"))
				.roles("ADMIN", "USER").build());
		userDetailsList.add(User.withUsername("andres").password(this.passwordEncoder.encode("password"))
				.roles("USER").build());

		return new InMemoryUserDetailsManager(userDetailsList);
	}*/
	
}

