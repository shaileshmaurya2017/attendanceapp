package com.attendance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.attendance.model.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig {

	private final UserDetailsServiceImpl userDetailsServiceImpl;
	
	
	public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
		
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder(){	
		return new BCryptPasswordEncoder();		
	}
	
	 @Bean
	 UserDetailsService userDetailsService() {
        return this.userDetailsServiceImpl;
     }
	 
	 @Bean
	 DaoAuthenticationProvider authenticationProvider() {	 
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
	 }
	 
	 @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	     
		// AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		 
//		 builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//		 AuthenticationManager manager = builder.build();
		 
		 /*http
		 	.authorizeHttpRequests()
		 	.requestMatchers("/index","/register","/doregister").permitAll()
		 	.requestMatchers("/adminreport").hasRole("admin")
		 	.requestMatchers("/attendencehome").hasAuthority("user")
		 	.anyRequest().authenticated()
		 	//.anyRequest().denyAll()
		 	.and()
		 	.formLogin()
		 	.loginPage("/")
		 	.loginProcessingUrl("/checklogin")
		 	.defaultSuccessUrl("/attendencehome", true)
		 	.permitAll()
		 	.and()
		 	.logout().permitAll()
		 	.and()
		 	.csrf().disable()
		 	.authenticationManager(manager)
		 	.httpBasic()
		 	;*/
		/* return http
		 	.formLogin(form -> form.loginPage("/").permitAll())
		 	.authorizeHttpRequests(auth->auth
		 			.requestMatchers("/register").permitAll()
		 			.requestMatchers("/doregister").permitAll()
		 			.requestMatchers("./static/*").permitAll()
		 			.requestMatchers("/checklogin").permitAll()
		 			//.requestMatchers("/*").authenticated()
		 			.anyRequest().authenticated()).build();*/
		 //http.httpBasic(Customizer.withDefaults());
		 http.authorizeHttpRequests(
	              //  auth -> auth.anyRequest().authenticated();
	        		Customizer ->{
	        			//Customizer.requestMatchers("/").authenticated();
	        			Customizer.requestMatchers("/","/register","/doregister","/checklogin","../static/").permitAll();
	        			Customizer.requestMatchers("/adminreport").hasAuthority("admin");
	        			Customizer.requestMatchers("/attendencehome").hasAuthority("user");
	        			//Customizer.requestMatchers("/checklogin").hasAuthority("user","admin");
	        			Customizer.requestMatchers("/attendancereportadmin/*","/*").authenticated();
	        			//Customizer.requestMatchers("/*").denyAll();
	        			Customizer.anyRequest().authenticated();
	        			
	        		}
	        		
	        		);
		 
	        http.formLogin(Customizer -> Customizer.loginPage("/").loginProcessingUrl("/checklogin")
	    		 	.defaultSuccessUrl("/attendencehome", true));
			
			http.csrf(Customizer -> Customizer.disable());

			HeaderWriterLogoutHandler clearsitedata =  new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.COOKIES));
			http.logout(logout->{
				logout.logoutUrl("/logout").permitAll();
				logout.logoutSuccessUrl("/");
				logout.invalidateHttpSession(true);
				logout.clearAuthentication(true);
				logout.addLogoutHandler(clearsitedata);
			});
			//http.formLogin(loginCustomizer->{loginCustomizer.loginPage("/");}); 
				      
			http.userDetailsService(userDetailsService());
		 return http.build();
	    }
	 

}
