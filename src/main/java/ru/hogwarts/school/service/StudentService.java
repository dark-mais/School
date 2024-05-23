package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private Long idCounter = 1L;

    public Student createStudent(Student student) {
        student.setId(idCounter++);
        students.put(student.getId(), student);
        return student;
    }

    public Optional<Student> getStudent(Long id) {
        return Optional.ofNullable(students.get(id));
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Student updateStudent(Long id, Student student) {
        if (students.containsKey(id)) {
            student.setId(id);
            students.put(id, student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(Long id) {
        return students.remove(id);
    }

    public Collection<Student> findStudentsByAge(int age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }
}
