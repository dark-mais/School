package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Long id, Student student) {
        student.setId(id);
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        studentRepository.deleteById(id);
        return null;
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentsByAge(Long age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getStudentsByAgeRange(Long min, Long max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public long getTotalStudents() {
        logger.info("Called method: getTotalStudents");
        return studentRepository.countAllStudents();
    }

    public double getAverageAge() {
        logger.info("Called method: getAverageAge");
        return studentRepository.findAverageAge();
    }

    public Collection<Student> getLastFiveStudents() {
        logger.info("Called method: getLastFiveStudents");
        return studentRepository.findLastFiveStudents();
    }
}
