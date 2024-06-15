package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT COUNT(s) FROM Student s")
    long countAllStudents();

    @Query("SELECT AVG(s.age) FROM Student s")
    double findAverageAge();

    @Query(value = "SELECT * FROM Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> findLastFiveStudents();

    Collection<Student> findByAgeBetween(Long min, Long max);

    Collection<Student> findByAge(Long age);
}
