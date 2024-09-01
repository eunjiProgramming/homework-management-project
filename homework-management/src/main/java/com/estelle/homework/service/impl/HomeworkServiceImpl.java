package com.estelle.homework.service.impl;

import com.estelle.homework.dto.HomeworkDto;
import com.estelle.homework.entity.Homework;
import com.estelle.homework.exception.ResourceNotFoundException;
import com.estelle.homework.repository.HomeworkRepository;
import com.estelle.homework.service.HomeworkService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final ModelMapper modelMapper;

    @Override
    public HomeworkDto addHomework(HomeworkDto homeworkDto) {
        // HomeworkDto를 Homework 엔티티로 변환
        Homework homework = modelMapper.map(homeworkDto, Homework.class);

        // Homework 엔티티 저장
        Homework savedHomework = homeworkRepository.save(homework);

        // 저장된 Homework 엔티티를 HomeworkDto로 변환하여 반환
        return modelMapper.map(savedHomework, HomeworkDto.class);
    }

    @Override
    public HomeworkDto getHomework(Long id) {
        // 주어진 ID로 Homework 엔티티 조회, 없을 시 예외 발생
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 숙제를 찾을 수 없습니다: " + id));

        // 조회된 Homework 엔티티를 HomeworkDto로 변환하여 반환
        return modelMapper.map(homework, HomeworkDto.class);
    }

    @Override
    public List<HomeworkDto> getAllHomeworks() {
        // 모든 Homework 엔티티 조회
        List<Homework> homeworks = homeworkRepository.findAll();

        // 조회된 Homework 엔티티 목록을 HomeworkDto 목록으로 변환하여 반환
        return homeworks.stream()
                .map(homework -> modelMapper.map(homework, HomeworkDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public HomeworkDto updateHomework(HomeworkDto homeworkDto, Long id) {
        // 주어진 ID로 Homework 엔티티 조회, 없을 시 예외 발생
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 숙제를 찾을 수 없습니다: " + id));

        // Homework 엔티티의 필드 업데이트
        homework.setTitle(homeworkDto.getTitle());
        homework.setDescription(homeworkDto.getDescription());
        homework.setInstructor(homeworkDto.getInstructor());
        homework.setClassName(homework.getClassName());
        homework.setCompleted(homeworkDto.isCompleted());

        // 업데이트된 Homework 엔티티 저장
        Homework updatedHomework = homeworkRepository.save(homework);

        // 저장된 Homework 엔티티를 HomeworkDto로 변환하여 반환
        return modelMapper.map(updatedHomework, HomeworkDto.class);
    }

    @Override
    public void deleteHomework(Long id) {
        // 주어진 ID로 Homework 엔티티 조회, 없을 시 예외 발생
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 숙제를 찾을 수 없습니다: " + id));

        // Homework 엔티티 삭제
        homeworkRepository.deleteById(id);
    }

    @Override
    public HomeworkDto completeHomework(Long id) {
        // 주어진 ID로 Homework 엔티티 조회, 없을 시 예외 발생
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 숙제를 찾을 수 없습니다: " + id));

        // Homework 상태를 '완료'로 변경
        homework.setCompleted(Boolean.TRUE);

        // 업데이트된 Homework 엔티티 저장
        Homework updatedHomework = homeworkRepository.save(homework);

        // 저장된 Homework 엔티티를 HomeworkDto로 변환하여 반환
        return modelMapper.map(updatedHomework, HomeworkDto.class);
    }

    @Override
    public HomeworkDto inCompleteHomework(Long id) {
        // 주어진 ID로 Homework 엔티티 조회, 없을 시 예외 발생
        Homework homework = homeworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID로 숙제를 찾을 수 없습니다: " + id));

        // Homework 상태를 '미완료'로 변경
        homework.setCompleted(Boolean.FALSE);

        // 업데이트된 Homework 엔티티 저장
        Homework updatedHomework = homeworkRepository.save(homework);

        // 저장된 Homework 엔티티를 HomeworkDto로 변환하여 반환
        return modelMapper.map(updatedHomework, HomeworkDto.class);
    }
}
