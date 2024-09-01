package com.estelle.homework.controller;

import com.estelle.homework.dto.HomeworkDto;
import com.estelle.homework.service.HomeworkService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*") // CORS 허용
@RestController // 이 클래스가 REST 컨트롤러임을 나타냄
@RequestMapping("api/homeworks") // 기본 URL 매핑 설정
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자를 자동으로 생성 (의존성 주입을 쉽게 하기 위함)
public class HomeworkController {

    private final HomeworkService homeworkService; // Homework 비즈니스 로직을 처리하는 서비스

    // Homework 추가 REST API
    @PreAuthorize("hasRole('INSTRUCTOR')") // 이 메서드는 INSTRUCTOR 역할을 가진 사용자만 접근 가능
    @PostMapping
    public ResponseEntity<HomeworkDto> addHomework(@RequestBody HomeworkDto homeworkDto){
        HomeworkDto savedHomework = homeworkService.addHomework(homeworkDto);
        return new ResponseEntity<>(savedHomework, HttpStatus.CREATED); // 성공적으로 생성된 경우 201 상태 반환
    }

    // Homework 조회 REST API
    @PreAuthorize("hasAnyRole('INSTRUCTOR','STUDENT')") // 이 메서드는 INSTRUCTOR 또는 STUDENT 역할을 가진 사용자만 접근 가능
    @GetMapping("{id}")
    public ResponseEntity<HomeworkDto> getHomework(@PathVariable("id") Long homeworkId){
        HomeworkDto homeworkDto = homeworkService.getHomework(homeworkId);
        return new ResponseEntity<>(homeworkDto, HttpStatus.OK); // 성공적으로 조회된 경우 200 상태 반환
    }

    // 모든 Homework 조회 REST API
    @PreAuthorize("hasAnyRole('INSTRUCTOR','STUDENT')") // 이 메서드는 INSTRUCTOR 또는 STUDENT 역할을 가진 사용자만 접근 가능
    @GetMapping
    public ResponseEntity<List<HomeworkDto>> getAllHomeworks(){
        List<HomeworkDto> homeworks = homeworkService.getAllHomeworks();
        return ResponseEntity.ok(homeworks); // 모든 Homework를 조회한 경우 200 상태 반환
    }

    // Homework 업데이트 REST API
    @PreAuthorize("hasRole('INSTRUCTOR')") // 이 메서드는 INSTRUCTOR 역할을 가진 사용자만 접근 가능
    @PutMapping("{id}")
    public ResponseEntity<HomeworkDto> updateHomework(@RequestBody HomeworkDto homeworkDto, @PathVariable("id") Long homeworkId){
        HomeworkDto updatedHomework = homeworkService.updateHomework(homeworkDto, homeworkId);
        return ResponseEntity.ok(updatedHomework); // 성공적으로 업데이트된 경우 200 상태 반환
    }

    // Homework 삭제 REST API
    @PreAuthorize("hasRole('INSTRUCTOR')") // 이 메서드는 INSTRUCTOR 역할을 가진 사용자만 접근 가능
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteHomework(@PathVariable("id") Long homeworkId){
        homeworkService.deleteHomework(homeworkId);
        return ResponseEntity.ok("Homework이 성공적으로 삭제되었습니다!."); // 성공적으로 삭제된 경우 200 상태 반환
    }

    // Homework 완료 처리 REST API
    @PreAuthorize("hasAnyRole('INSTRUCTOR','STUDENT')") // 이 메서드는 INSTRUCTOR 또는 STUDENT 역할을 가진 사용자만 접근 가능
    @PatchMapping("{id}/complete")
    public ResponseEntity<HomeworkDto> completeHomework(@PathVariable("id") Long homeworkId){
        HomeworkDto updatedHomework = homeworkService.completeHomework(homeworkId);
        return ResponseEntity.ok(updatedHomework); // 성공적으로 완료된 경우 200 상태 반환
    }

    // Homework 미완료 처리 REST API
    @PreAuthorize("hasAnyRole('INSTRUCTOR','STUDENT')") // 이 메서드는 INSTRUCTOR 또는 STUDENT 역할을 가진 사용자만 접근 가능
    @PatchMapping("{id}/in-complete")
    public ResponseEntity<HomeworkDto> inCompleteHomework(@PathVariable("id") Long homeworkId){
        HomeworkDto updatedHomework = homeworkService.inCompleteHomework(homeworkId);
        return ResponseEntity.ok(updatedHomework); // 성공적으로 미완료 처리된 경우 200 상태 반환
    }

}
