package com.estelle.homework.service.impl;

import com.estelle.homework.dto.JwtAuthResponse;
import com.estelle.homework.dto.LoginDto;
import com.estelle.homework.dto.RegisterDto;
import com.estelle.homework.entity.Role;
import com.estelle.homework.entity.User;
import com.estelle.homework.exception.HomeworkAPIException;
import com.estelle.homework.repository.RoleRepository;
import com.estelle.homework.repository.UserRepository;
import com.estelle.homework.security.JwtTokenProvider;
import com.estelle.homework.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service // 이 클래스가 서비스 레이어의 빈임을 선언
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자를 자동으로 생성 (의존성 주입을 쉽게 하기 위함)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository; // 사용자 정보 저장소
    private final RoleRepository roleRepository; // 역할 정보 저장소
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화
    private final AuthenticationManager authenticationManager; // 인증 매니저
    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰 프로바이더

    @Override
    public String register(RegisterDto registerDto) {

        // 데이터베이스에 이미 존재하는 사용자 이름인지 확인
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new HomeworkAPIException(HttpStatus.BAD_REQUEST, "사용자 이름이 이미 존재합니다!");
        }

        // 데이터베이스에 이미 존재하는 이메일인지 확인
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new HomeworkAPIException(HttpStatus.BAD_REQUEST, "이메일이 이미 존재합니다!");
        }

        // 새로운 사용자 생성 및 정보 설정
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword())); // 비밀번호 암호화 후 설정

        // 사용자 역할 설정 (기본 역할: ROLE_STUDENT)
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_STUDENT");
        roles.add(userRole);
        user.setRoles(roles);

        // 사용자 정보 저장
        userRepository.save(user);

        return "사용자가 성공적으로 등록되었습니다!"; // 성공 메시지 반환
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {

        // 사용자 인증 수행
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        // 인증 정보를 SecurityContext에 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(authentication);

        // 사용자 정보 조회 및 역할 가져오기
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),
                loginDto.getUsernameOrEmail());

        String role = null;
        if(userOptional.isPresent()){
            User loggedInUser = userOptional.get();
            Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();

            if(optionalRole.isPresent()){
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }

        // 인증 응답 생성
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);

        return jwtAuthResponse; // JWT 토큰 및 역할 정보를 포함한 응답 반환
    }
}