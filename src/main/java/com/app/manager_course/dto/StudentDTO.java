package com.app.manager_course.dto;

import com.app.manager_course.models.Student;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentDTO {

    private List<Student> students;

    @Min(0)
    private int page;

    @Min(1)
    private int size;

    @Min(0)
    private int total;

    public StudentDTO(List<Student> students, int page, int size, int total) {
        this.students = students;
        this.page = page;
        this.size = size;
        this.total = total;
    }
}
