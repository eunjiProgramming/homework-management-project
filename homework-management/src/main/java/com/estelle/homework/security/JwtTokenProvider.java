package com.estelle.homework.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;

@Component // 스프링 빈으로 등록
public class JwtTokenProvider {

    @Value("${app.jwt-secret}") // 애플리케이션 설정 파일에서 JWT 비밀키를 가져옴
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}") // JWT 만료 시간을 설정 파일에서 가져옴
    private long jwtExpirationDate;

    // JWT 토큰을 생성하는 메서드
    public String generateToken(Authentication authentication){
        String username = authentication.getName(); // 인증 객체에서 사용자 이름을 가져옴

        Date currentDate = new Date(); // 현재 시간

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate); // 만료 시간 설정

        // JWT 토큰을 생성하고 반환
        String token = Jwts.builder()
                .setSubject(username) // 사용자 이름을 주제로 설정
                .setIssuedAt(currentDate) // 토큰 발행 시간 설정
                .setExpiration(expireDate) // 토큰 만료 시간 설정
                .signWith(key()) // 서명 키 설정
                .compact();

        return token;
    }

    // JWT 서명 키를 생성하는 메서드
    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret) // 비밀키를 디코딩하여 HMAC-SHA 키 생성
        );
    }

    // JWT 토큰에서 사용자 이름을 추출하는 메서드
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key()) // 서명 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱
                .getBody();

        String username = claims.getSubject(); // 주제(사용자 이름) 추출

        return username;
    }

    // JWT 토큰의 유효성을 검사하는 메서드
    public boolean validateToken(String token){
        Jwts.parserBuilder()
                .setSigningKey(key()) // 서명 키 설정
                .build()
                .parse(token); // 토큰 파싱을 통해 유효성 검사
        return true; // 예외가 발생하지 않으면 토큰이 유효함
    }

}