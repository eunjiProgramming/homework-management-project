package com.estelle.homework.service;

import com.estelle.homework.dto.HomeworkDto;

import java.util.List;

public interface HomeworkService {
    HomeworkDto addHomework(HomeworkDto homeworkDto);

    HomeworkDto getHomework(Long id);

    List<HomeworkDto> getAllHomeworks();

    HomeworkDto updateHomework(HomeworkDto homeworkDto, Long id);

    void deleteHomework(Long id);

    HomeworkDto completeHomework(Long id);

    HomeworkDto inCompleteHomework(Long id);

}
