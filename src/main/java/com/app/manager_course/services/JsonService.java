package com.app.manager_course.services;

import com.app.manager_course.dto.StudentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.app.manager_course.models.Student;
import com.app.manager_course.models.UniversityData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JsonService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String fileName = "./uploads/npru_course_se_53.json";

    public UniversityData readJsonFile() throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("File not found: " + fileName);
        }
        return objectMapper.readValue(file, UniversityData.class);
    }

    public void writeJsonFile(UniversityData data) throws IOException {
        File file = new File(fileName);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
    }

    public List<Student> findAllStudents() throws IOException {
        return readJsonFile().getStudents();
    }

    public Student findStudentById(String id) throws IOException {
        return readJsonFile().getStudents().stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public StudentDTO getStudents(String keyword, int page, int size) throws IOException {
        List<Student> students = new ArrayList<>();
        students = findAllStudents();
        if (keyword != "" && !keyword.isEmpty()){
            students = students.stream().filter(stu -> stu.getName().toLowerCase().contains(keyword.toLowerCase()) || stu.getId().contains(keyword)).collect(Collectors.toList());
        }

        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), students.size());
        int total = students.size();

        List<Student> paginatedList = students.subList(start, end);
        Page<Student> studentPage = new PageImpl<>(paginatedList, pageable, students.size());

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudents(studentPage.getContent());
        studentDTO.setPage(page);
        studentDTO.setSize(size);
        studentDTO.setTotal(total);
        return studentDTO;
    }

    public void addStudentToJsonFile(Student student) throws IOException {
        UniversityData data = readJsonFile();
        data.getStudents().add(student);
        writeJsonFile(data);
    }

    public void updateStudent(String id, Student student) throws IOException {
        UniversityData data = readJsonFile();
        List<Student> students = data.getStudents();

        for (Student stu : students) {
            if (stu.getId().equals(id)) {
                stu.setName(student.getName());
                break;
            }
        }

        writeJsonFile(data);
    }

    public void deleteStudent(String id) throws IOException {
        UniversityData data = readJsonFile();
        List<Student> updatedStudents = data.getStudents().stream()
                .filter(stu -> !stu.getId().equals(id))
                .collect(Collectors.toList());

        if (updatedStudents.size() == data.getStudents().size()) {
            System.out.println("Student not found: " + id);
            return;
        }

        data.setStudents(updatedStudents);
        writeJsonFile(data);
        System.out.println("Student deleted successfully: " + id);
    }
}
