package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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

    public Collection<Student> getLastFiveStudents() {
        logger.info("Called method: getLastFiveStudents");
        return studentRepository.findLastFiveStudents();
    }

    public List<String> getNamesStartingWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAverageAge() {
        // logger.info("Called method: getAverageAge");
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    public void printStudentsInParallel() {
        List<Student> students = studentRepository.findAll();
        if (students.size() < 6) {
            logger.warn("Not enough students to print.");
            return;
        }

        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        });

        executorService.submit(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        });

        executorService.shutdown();
    }

    public synchronized void printStudentName(String name) {
        System.out.println(name);
    }

    public void printStudentsSynchronized() {
        List<Student> students = studentRepository.findAll();
        if (students.size() < 6) {
            logger.warn("Not enough students to print.");
            return;
        }

        printStudentName(students.get(0).getName());
        printStudentName(students.get(1).getName());

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            printStudentName(students.get(2).getName());
            printStudentName(students.get(3).getName());
        });

        executorService.submit(() -> {
            printStudentName(students.get(4).getName());
            printStudentName(students.get(5).getName());
        });

        executorService.shutdown();
    }
}
