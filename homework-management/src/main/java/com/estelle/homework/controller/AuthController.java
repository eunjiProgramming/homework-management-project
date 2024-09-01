package com.estelle.homework.controller;

import com.estelle.homework.dto.JwtAuthResponse;
import com.estelle.homework.dto.LoginDto;
import com.estelle.homework.dto.RegisterDto;
import com.estelle.homework.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*") // 모든 도메인에서의 CORS 요청을 허용
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자를 자동으로 생성 (의존성 주입을 쉽게 하기 위함)
@RestController // 이 클래스가 REST 컨트롤러임을 나타냄
@RequestMapping("/api/auth") // /api/auth 경로에 매핑
public class AuthController {

    private final AuthService authService; // 인증 관련 비즈니스 로직을 처리하는 서비스

    // 사용자 등록을 처리하는 REST API
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto); // 사용자 등록 처리
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 성공적으로 생성된 경우 201 상태 반환
    }

    // 사용자 로그인을 처리하는 REST API
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        JwtAuthResponse jwtAuthResponse = authService.login(loginDto); // 사용자 로그인 처리 및 JWT 토큰 생성
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK); // 성공적으로 로그인된 경우 200 상태 반환
    }

}
