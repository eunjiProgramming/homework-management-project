package com.estelle.homework.service;

import com.estelle.homework.dto.JwtAuthResponse;
import com.estelle.homework.dto.LoginDto;
import com.estelle.homework.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);
}