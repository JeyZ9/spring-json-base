package com.app.manager_course.controllers;

import com.app.manager_course.dto.StudentDTO;
import com.app.manager_course.models.Student;
import com.app.manager_course.services.JsonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin("http://localhost:5173")
public class UniversityController {

    private final JsonService jsonService;

    public UniversityController(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() throws IOException {
        return ResponseEntity.ok(jsonService.findAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) throws IOException {
        Student student = jsonService.findStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/search")
    public ResponseEntity<StudentDTO> searchStudents(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size) throws IOException {
        return ResponseEntity.ok(jsonService.getStudents(keyword, page, size));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createStudent(@RequestBody Student student) throws IOException {
        jsonService.addStudentToJsonFile(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student created successfully.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) throws IOException {
        jsonService.updateStudent(id, updatedStudent);
        return ResponseEntity.status(HttpStatus.OK).body("Student updated successfully.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) throws IOException {
        jsonService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.OK).body("Student deleted successfully.");
    }
}
