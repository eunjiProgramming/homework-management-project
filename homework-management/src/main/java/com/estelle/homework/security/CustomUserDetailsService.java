package com.estelle.homework.security;

import com.estelle.homework.entity.User;
import com.estelle.homework.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service // 이 클래스가 서비스 계층의 빈으로 등록됨
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자를 자동으로 생성 (의존성 주입 편리)
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository; // User 정보를 가져오기 위한 리포지토리

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        // username이나 email로 User를 조회, 없으면 예외 발생
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("입력하신 사용자 이름이나 이메일로 등록된 사용자가 존재하지 않습니다."));

        // User의 Role 정보를 Spring Security의 GrantedAuthority로 변환
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // UserDetails 객체 생성 및 반환 (Spring Security에서 사용)
        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.getPassword(),
                authorities
        );
    }
}