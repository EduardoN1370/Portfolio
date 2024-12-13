package com.microservice.course.Service;

import com.microservice.course.Entity.Course;
import com.microservice.course.Http.Response.StudentByCourseResponse;

import java.util.List;

public interface ICourseService {

    List<Course> findAll();
    Course findById(Long id);
    void save(Course course);
    StudentByCourseResponse findStudentByIdCourse(Long idCourse);



}
