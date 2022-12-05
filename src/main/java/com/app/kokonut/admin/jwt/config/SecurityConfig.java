package com.app.kokonut.admin.jwt.config;

import com.app.kokonut.admin.jwt.been.JwtAccessDeniedHandler;
import com.app.kokonut.admin.jwt.been.JwtAuthenticationEntryPoint;
import com.app.kokonut.admin.jwt.been.JwtAuthenticationFilter;
import com.app.kokonut.admin.jwt.been.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Woody
 * Date : 2022-11-01
 * Time :
 * Remark : 기본 시큐리티셋팅
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // JWT 필터 및 권한 제외 url
        return (web) -> web.ignoring().antMatchers("/favicon.ico","/swagger*/**","/v2/api-docs","/webjars/**", "/api/Auth/**");
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // Exception handling 할 때 만든 클래스를 추가
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeRequests()
                .antMatchers("/", "/swagger-ui/index.html/**", "/api/Admin/**").permitAll()
                .antMatchers("/api/Admin/masterTest").hasAnyAuthority("ROLE_MASTER")
                .antMatchers("/api/Admin/adminTest").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}


