package org.example.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //여기에 작성할것들이 필터가 된다 기존에 작성한것을 체인지 한다.

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                //csrf 비활성화
                .authorizeHttpRequests((request)-> {
                    request.requestMatchers("/user/**").authenticated();//인증이 필요하다.
                    request.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER") ;// 이러한 권한이 필요하다.
                    request.requestMatchers("/admin/**").hasRole("ADMIN");
                    request.anyRequest().permitAll(); // 위 주소 배꼬는 다 통과
                    

                }
                )

                //로그인페이지
                .formLogin((login)->{
                    login.loginPage("/login");
                })


                .build();



    }

}
