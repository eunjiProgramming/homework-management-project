package com.estelle.homework.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // 스프링 빈으로 등록 (JWT 인증 필터)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider; // JWT 토큰 유효성 검사 및 정보 추출을 위한 프로바이더

    private UserDetailsService userDetailsService; // 사용자 정보를 로드하기 위한 서비스

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // HTTP 요청에서 JWT 토큰을 가져옴
        String token = getTokenFromRequest(request);

        // 토큰 유효성 검사
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            // 토큰에서 사용자 이름 추출
            String username = jwtTokenProvider.getUsername(token);

            // 사용자 이름으로 사용자 정보 로드
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 인증 토큰 생성 (비밀번호는 null로 설정)
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // 요청 세부 정보 설정
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // SecurityContext에 인증 정보 설정
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // 다음 필터 체인 실행
        filterChain.doFilter(request, response);
    }

    // HTTP 요청에서 Authorization 헤더를 확인하고, JWT 토큰을 추출하는 메서드
    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        // Bearer로 시작하는 경우에만 토큰을 반환
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }

        return null;
    }
}
