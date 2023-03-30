package com.company.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
// 시큐리티 필터 등록
@EnableWebSecurity
@RequiredArgsConstructor
// 특정 페이지에 특정 권한이 있는 유머나 접근 허용할 경우 권한 및 인증을 미리 체크하겠다는 설정
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    // 비밀번호 암호화를 위해 사용하는 빈
    // 회원가입시에 사용해서 비밀번호를 암호화해야 비교할 수 있음
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // csrf 토큰을 비활성화
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/member/**", "/login/**", "/js/**", "/css/**", "/img/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .usernameParameter("un")
                .passwordParameter("pw")
                .loginPage("/member/login")
                .loginProcessingUrl("/member/login/action")
                .defaultSuccessUrl("/member/success")
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/member/login")
                // 인증정보를 지우고 세션을 무효화
                .invalidateHttpSession(true)
                // JSESSIONID, remember-me 쿠키 삭제
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll()
            .and()
                .sessionManagement()
                // 세션 최대 허용 수 1, -1인 경우 무제한 생성
                .maximumSessions(1)
                // true면 중복 로그인을 막고, false면 이전 로그인의 세션을 해제
                .maxSessionsPreventsLogin(false)
                // 세션이 만료될 경우 이동 할 페이지 지정
                .expiredUrl("/member/login?error=true&exception=Have been attempted to login from a new place. or session expired")
            .and()
                // 로그인 유지
            .and().rememberMe()
                // 항상 기억할 것인지 여부
                .alwaysRemember(false)
                // 12시간 유지
                .tokenValiditySeconds(43200)
                .rememberMeParameter("remember-me")
                .userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
