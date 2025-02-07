package com.app.manager_course.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UniversityData {
    private String university;
    private String system;
    private AcademicSemester academic_semester;
    private Course course;
    private List<Student> students;
    private String page;

//    // Getter & Setter
//    public String getUniversity() { return university; }
//    public void setUniversity(String university) { this.university = university; }
//
//    public String getSystem() { return system; }
//    public void setSystem(String system) { this.system = system; }
//
//    public AcademicSemester getAcademic_semester() { return academic_semester; }
//    public void setAcademic_semester(AcademicSemester academic_semester) { this.academic_semester = academic_semester; }
//
//    public Course getCourse() { return course; }
//    public void setCourse(Course course) { this.course = course; }
//
//    public List<Student> getStudents() { return students; }
//    public void setStudents(List<Student> students) { this.students = students; }
//
//    public String getPage() { return page; }
//    public void setPage(String page) { this.page = page; }
}
