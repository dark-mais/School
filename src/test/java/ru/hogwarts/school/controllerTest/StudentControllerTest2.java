package ru.hogwarts.school.controllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest2 {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createStudentTest() {
        Student student = new Student(null, "Harry Potter", 11);
        ResponseEntity<Student> response = restTemplate.postForEntity("/student", student, Student.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getName()).isEqualTo("Harry Potter");
    }

    @Test
    public void getStudentTest() {
        ResponseEntity<Student> response = restTemplate.getForEntity("/student/{id}", Student.class, 1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getId()).isEqualTo(1L);
    }

    @Test
    public void getAllStudentsTest() {
        ResponseEntity<Collection> response = restTemplate.getForEntity("/student", Collection.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void updateStudentTest() {
        Student student = new Student(1L, "Harry Potter", 12);
        HttpEntity<Student> requestUpdate = new HttpEntity<>(student);
        ResponseEntity<Student> response = restTemplate.exchange("/student/{id}", HttpMethod.PUT, requestUpdate, Student.class, 1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getAge()).isEqualTo(12);
    }

    @Test
    public void deleteStudentTest() {
        ResponseEntity<Student> response = restTemplate.exchange("/student/{id}", HttpMethod.DELETE, null, Student.class, 1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void findStudentsByAgeTest() {
        ResponseEntity<Collection> response = restTemplate.getForEntity("/student/age/{age}", Collection.class, 11L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void getStudentsByAgeRangeTest() {
        ResponseEntity<Collection> response = restTemplate.getForEntity("/student/between-ages?min={min}&max={max}", Collection.class, 10L, 12L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void getFacultyByStudentIdTest() {
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/student/{id}/faculty", Faculty.class, 1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
}
