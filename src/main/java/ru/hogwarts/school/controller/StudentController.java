package ru.hogwarts.school.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/names-starting-with-a")
    public List<String> getNamesStartingWithA() {
        return studentService.getNamesStartingWithA();
    }

    @GetMapping("/average-age")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public Optional<Student> getStudent(@PathVariable Long id) {
        return Optional.ofNullable(studentService.getStudent(id));
    }

    @GetMapping
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PutMapping("{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/age/{age}")
    public Collection<Student> findStudentsByAge(@PathVariable Long age) {
        return studentService.findStudentsByAge(age);
    }

    @GetMapping("/between-ages")
    public Collection<Student> getStudentsByAgeRange(@RequestParam Long min, @RequestParam Long max) {
        return studentService.getStudentsByAgeRange(min, max);
    }

    @GetMapping("{id}/faculty")
    public Faculty getFacultyByStudentId(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        return student != null ? student.getFaculty() : null;
    }


}
