package com.estelle.homework.config;

import com.estelle.homework.security.JwtAuthenticationEntryPoint;
import com.estelle.homework.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 스프링 설정 클래스임을 알려줌
@EnableMethodSecurity // 메소드 단위로 보안 설정을 가능하게 해줌
@AllArgsConstructor // 생성자 자동 생성 (의존성 주입을 쉽게 하기 위함)
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService; // 사용자 정보를 로드하기 위한 서비스

    private JwtAuthenticationEntryPoint authenticationEntryPoint; // 인증 실패 시 동작할 엔트리 포인트

    private JwtAuthenticationFilter authenticationFilter; // JWT 인증 필터

    @Bean
    public static PasswordEncoder passwordEncoder(){
        // 비밀번호 암호화용 빈, BCrypt 사용
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable()) // CSRF 방어 비활성화
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/api/auth/**").permitAll(); // 인증 없이 접근 가능한 경로 설정
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); // OPTIONS 메서드에 대한 모든 요청 허용
                    authorize.anyRequest().authenticated(); // 나머지 요청은 인증 필요
                }).httpBasic(Customizer.withDefaults()); // 기본 HTTP 인증 사용

        http.exceptionHandling( exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint)); // 인증 실패 시 동작할 엔트리 포인트 설정

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 전에 추가

        return http.build(); // SecurityFilterChain 객체를 빌드하고 반환
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // AuthenticationManager 빈 등록
        return configuration.getAuthenticationManager();
    }

}
