package com.microservice.course.Controller;

import com.microservice.course.Entity.Course;
import com.microservice.course.Service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private ICourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<?> saveCourse(@RequestBody Course course) {
        courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAllCourses() {
        List<Course> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Course> findByid(@PathVariable Long id) {
        Course course = courseService.findById(id);
        return ResponseEntity.ok(course);
    }
    @GetMapping("/search-student/{idCourse}")
    public ResponseEntity<?> findStudentsByIdCourseId(@PathVariable Long idCourse) {
        return ResponseEntity.ok(courseService.findStudentByIdCourse(idCourse));
    }

}
