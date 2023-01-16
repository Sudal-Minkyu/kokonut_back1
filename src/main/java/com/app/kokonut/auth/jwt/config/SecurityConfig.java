package com.app.kokonut.auth.jwt.config;

import com.app.kokonut.auth.jwt.been.JwtAccessDeniedHandler;
import com.app.kokonut.auth.jwt.been.JwtAuthenticationEntryPoint;
import com.app.kokonut.auth.jwt.been.JwtAuthenticationFilter;
import com.app.kokonut.auth.jwt.been.JwtTokenProvider;
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
        return (web) -> web.ignoring().antMatchers(
                // 필터 제외항목 API
                "/favicon.ico","/swagger*/**","/v2/api-docs","/webjars/**", "/api/Auth/**", "/api/NiceId/**",
                // 임시 제외항목 API
                "/api/ApiKey/**", "/api/Qna/**", "/api/Notice/**", "/api/RevisedDocument/**",
                "/api/DynamicUser/**", "/api/DynamicRemove/**", "/api/DynamicDormant/**",
                "/api/TotalDbDownload/**");
    }

//    @Bean -> Post는 무조건 막는데 확인해봐야됨
//    @Order(0)
//    public SecurityFilterChain resources(HttpSecurity http) throws Exception {
//    return http.requestMatchers(matchers -> matchers
//                    .antMatchers("/favicon.ico","/swagger*/**","/v2/api-docs","/webjars/**",
//                            "/api/Auth/**", "/api/NiceId/**"
//                            // 임시로 해둔 API들
//                            , "/api/ApiKey/**", "/api/Qna/**", "/api/Notice/**", "/api/RevisedDocument/**",
//                            "/api/DynamicUser/**", "/api/DynamicRemove/**", "/api/DynamicDormant/**"))
//            .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
//            .requestCache(RequestCacheConfigurer::disable)
//            .securityContext(AbstractHttpConfigurer::disable)
//            .sessionManagement(AbstractHttpConfigurer::disable)
//            .build();
//    }

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
            .antMatchers("/", "/swagger-ui/index.html/**", "/api/AlimtalkTemplate/**").permitAll()
            .antMatchers("/api/Admin/masterTest", "/api/Admin/**", "/api/AlimtalkMessage/**"
                    , "/api/AlimtalkTemplate/**", "/api/KakaoChannel/**").hasAnyAuthority("MASTER", "SYSTEM")
            .antMatchers("/api/Admin/adminTest").hasAnyAuthority("ADMIN","SYSTEM")
            .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}


