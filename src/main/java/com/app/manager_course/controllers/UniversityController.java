package com.app.manager_course.controllers;

import com.app.manager_course.models.Student;
import com.app.manager_course.models.UniversityData;
import com.app.manager_course.services.JsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class UniversityController {


    @Autowired
    private JsonService jsonService;

    public UniversityController(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    @Value("${upload-dir}")
    private String pathDir;

    private final String fileName = "npru_course_se_53.json";

    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            File file = new File(pathDir, fileName);
            List<Student> students = jsonService.findAllStudent(file.getAbsolutePath());
            return ResponseEntity.ok(students);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") String id) {
        try {
            File file = new File(pathDir, fileName);
            Student student = jsonService.findStudentById(id, file.getAbsolutePath());
            return ResponseEntity.ok(student);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping()
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        try {
            File file = new File(pathDir, fileName);
            String filePath = file.getAbsolutePath();
            UniversityData data = jsonService.readJsonFile(filePath);

            boolean exists = data.getStudents().stream()
                    .anyMatch(stu -> stu.getId().equals(student.getId()));

            if (!exists) {
                data.getStudents().add(student);
                jsonService.writeJsonFile(filePath, data);
                System.out.println("Student added successfully: " + student.getId());
                return ResponseEntity.ok("Student added successfully!");
            } else {
                System.out.println("Student already exists: " + student.getId());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Student already exists!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding student");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable("id") String id, @RequestBody Student student) {
        try {
            File file = new File(pathDir, fileName);
            jsonService.updateStudent(id, file.getAbsolutePath(), student);
            return ResponseEntity.ok("Student updated successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating student");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") String id) {
        try {
            File file = new File(pathDir, fileName);
            jsonService.deleteStudent(id, file.getAbsolutePath());
            return ResponseEntity.ok("Student deleted successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting student");
        }
    }

}
