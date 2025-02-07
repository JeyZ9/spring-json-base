package com.app.manager_course.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.manager_course.models.Student;
import com.app.manager_course.models.UniversityData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JsonService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public UniversityData readJsonFile(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), UniversityData.class);
    }

    public void writeJsonFile(String filePath, UniversityData data) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
    }

    public List<Student> findAllStudent(String filePath) throws IOException {
        return readJsonFile(filePath).getStudents();
    }

    public Student findStudentById(String id, String filePath) throws IOException {
        return readJsonFile(filePath).getStudents().stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addStudentToJsonFile(String filePath, Student student) throws IOException {
        UniversityData data = readJsonFile(filePath);
        data.getStudents().add(student);
        writeJsonFile(filePath, data);
    }

    public void updateStudent(String id, String filePath, Student updatedStudent) throws IOException {
        UniversityData data = readJsonFile(filePath);
        List<Student> students = data.getStudents();

        for (Student stu : students) {
            if (stu.getId().equals(id)) {
                stu.setName(updatedStudent.getName());
                break;
            }
        }

        writeJsonFile(filePath, data);
    }

    public void deleteStudent(String id, String filePath) throws IOException {
        UniversityData data = readJsonFile(filePath);

        List<Student> updatedStudents = data.getStudents().stream()
                .filter(stu -> !stu.getId().equals(id)) // ลบนักศึกษาที่มี ID ตรงกับที่กำหนด
                .collect(Collectors.toList());

        if (updatedStudents.size() == data.getStudents().size()) {
            System.out.println("Student not found: " + id);
            return;
        }

        data.setStudents(updatedStudents);
        writeJsonFile(filePath, data);

        System.out.println("Student deleted successfully: " + id);
    }

}
