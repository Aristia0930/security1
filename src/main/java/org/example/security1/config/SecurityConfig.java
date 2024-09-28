package org.example.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true) //securedEnabled 활성화
public class SecurityConfig {
    //여기에 작성할것들이 필터가 된다 기존에 작성한것을 체인지 한다.

    //해당 메서드의 리턴되는 오브젝트를 ioc로 등록해준다 bean dms
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                //csrf 비활성화
                .authorizeHttpRequests((request)-> {
                    request.requestMatchers("/user/**").authenticated();//인증이 필요하다. 인증만되면 들어가진다.
                    request.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER") ;// 이러한 권한이 필요하다.
                    request.requestMatchers("/admin/**").hasRole("ADMIN");
                    request.anyRequest().permitAll(); // 위 주소 배꼬는 다 통과
                    

                }
                )

                //로그인페이지
                .formLogin((login)->{
                    login.loginPage("/loginForm");
                    login.loginProcessingUrl("/login");
                    //login 호출되면 시큐리티가 낚아채서 대신 로그인을 진행한다.로그인을 진행한다.
                    login.defaultSuccessUrl("/");

                })


                .build();



    }

}
