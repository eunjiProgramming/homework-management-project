package com.estelle.homework;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication // Spring Boot 애플리케이션의 시작점을 나타냄
public class HomeworkManagementApplication {

	// ModelMapper 빈을 생성하는 메서드, 객체 간의 매핑을 쉽게 해줌
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	// 애플리케이션의 진입점 (main 메서드)
	public static void main(String[] args) {
		SpringApplication.run(HomeworkManagementApplication.class, args); // 애플리케이션 실행
	}

}
