package com.app.manager_course.models;

import lombok.Data;

@Data
public class Course {
    private String code;
    private String name;
    private String credits;
    private String section;
    private String instructor;
    private Schedule schedule;
    private String exam_schedule;
}

