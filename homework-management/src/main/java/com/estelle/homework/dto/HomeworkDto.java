package com.estelle.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkDto {
    private Long id;
    private String title;
    private String description;
    private String instructor;
    private String className;
    private boolean completed;
}
