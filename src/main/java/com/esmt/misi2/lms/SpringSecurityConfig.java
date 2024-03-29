package com.esmt.misi2.lms;

import com.esmt.misi2.lms.auth.handler.LoginSuccesHandler;
import com.esmt.misi2.lms.model.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
	
	@Autowired
	private LoginSuccesHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JpaUserDetailsService userDetailsServiceJpa;
	
	@Bean //autorisation
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().requestMatchers("/css/**", "/js/**", "/images/**", "/", "index", "/home", "/error", "/api/users/**", "/api/users/create-user", "/api/books/**", "/api/loans/**", "/api/loanrequests/**")
			.permitAll()
		.requestMatchers("/admin/dashboard").hasAnyRole("ADMIN")
		.requestMatchers("/books/create-book").hasAnyRole("ADMIN")
		.requestMatchers("/books/edit-book/**").hasAnyRole("ADMIN")
		.requestMatchers("/books/delete-book/**").hasAnyRole("ADMIN")
		.requestMatchers("/books/search-results/**").permitAll()
		.requestMatchers("/books/list-books").permitAll()
		.requestMatchers("/loans/**").hasAnyRole("ADMIN")
		.requestMatchers("/loanrequests/view-loan").hasAnyRole("USER")
		.requestMatchers("/loanrequests/user-requests").hasAnyRole("USER")
		.requestMatchers("/loanrequests/list-requests").hasAnyRole("ADMIN")
		.requestMatchers("/users/**").hasAnyRole("ADMIN")
		.requestMatchers("/register").permitAll()
		.requestMatchers("/register/success").permitAll()
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



