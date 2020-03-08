/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vegecipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Configuration
@EnableWebSecurity // <1>
@EnableGlobalMethodSecurity(prePostEnabled = true) // <2>
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { // <3>


	@Override
	protected void configure(HttpSecurity http) throws Exception { // <5>
		http
			.authorizeRequests()
				.antMatchers("/built/**", "/main.css", "/**" ).permitAll() //정적 웹 리소스를 차단할 이유가 없기 때문에 나열된 경로 에는 무조건 액세스 권한이 부여됩니다.
				.anyRequest().authenticated() // 위의 정책과 일치하지 않은 항목은 인증이 필요합니다.
				.and()
			.formLogin()
				.defaultSuccessUrl("/admin/**", true)
				.permitAll()
				.and()
			.httpBasic()
				.and()
			.csrf().disable()	// production 모드에서는 enable 해야함.
			.logout()
				.logoutSuccessUrl("/");
	}

}
// end::code[]
