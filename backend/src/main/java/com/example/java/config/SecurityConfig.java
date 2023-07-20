package com.example.java.config;

import com.example.java.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity  // 开启方法的安全访问权限
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPointImpl;
	@Autowired
	private AccessDeniedHandler accessDeniedHandlerImpl;
	// 授权请求
    @Bean
  	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
  		http
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement((policy)->{
					policy.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				});
		http
  		 	    .authorizeHttpRequests((requests) ->
					requests
							.requestMatchers("/user/**")
							.anonymous()
							.anyRequest()
							.authenticated()
  		 		);

		// 给系统添加过滤器，第二个参数是添加在哪个过滤器之前
		http.addFilterBefore(jwtTokenFilter,
				UsernamePasswordAuthenticationFilter.class);

		http.exceptionHandling((handle)-> {
			handle
					.authenticationEntryPoint(authenticationEntryPointImpl)
					.accessDeniedHandler(accessDeniedHandlerImpl);
		});


		// security 开启跨域
		http.cors(withDefaults());

		return http.build();
  	}

	// 开启密码密文存储
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 权限管理器
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



}

