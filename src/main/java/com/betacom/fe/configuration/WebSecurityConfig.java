package com.betacom.fe.configuration;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
//	@Autowired
//	RestTemplate rest;
//	
//	
//	@Value("${jpa.backend}")
//	String backend;
//	
//	
//	   @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        http.authorizeHttpRequests((requests) -> requests
//	                .requestMatchers("/amministratore", "/amministratore/**").hasRole("AMMINISTRATORE")
//	                .requestMatchers("/").permitAll()
//	                .anyRequest().authenticated()
//	                )
//	            .formLogin((form) -> form
//	                .loginPage("/login")
//	                .permitAll()
//	                )
//	            .logout((logout) -> logout.permitAll());
//	            
//	        return http.build();
//	    }
//	   
//		@Bean
//	    public UserDetailsService userDetailsService() {
//
//	        List<UserDetails> userDetails = new ArrayList<UserDetails>();
//
//	        URI uri = UriComponentsBuilder
//	                .fromHttpUrl(backend + "utente/listAll")  
//	                .buildAndExpand().toUri();      
//
//	        List<HashMap<String, Object>> r = rest.getForObject(uri, ArrayList.class);
//
//	        for (HashMap<String,Object> hashMap : r) {
//	            
//	            userDetails.add(
//	                User.withUsername(hashMap.get("mail").toString())
//	                   .password(passwordEncoder().encode(hashMap.get("password").toString()))
//	                   .roles(hashMap.get("ruolo").toString())
//	                   .build()
//	            );
//	        }
//	        
//	        return new InMemoryUserDetailsManager(userDetails);
//	   }
//	   
//	   @Bean
//		public PasswordEncoder passwordEncoder() {
//			return new BCryptPasswordEncoder();
//		}
//	   

//	@Bean
//	public UserDetailsService userDetailService() {
//		
//		UserDetails user = User.withUsername("user")
//				.password(passwordEncoder().encode("pwd"))
//				.roles("USER")
//				.build();
//		
//		UserDetails user2 = User.withUsername("user2")
//				.password(passwordEncoder().encode("provapwd"))
//				.roles("USER")
//				.build();
//		
//		UserDetails admin = User.withUsername("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user, user2, admin);
//	}
	
	

}
