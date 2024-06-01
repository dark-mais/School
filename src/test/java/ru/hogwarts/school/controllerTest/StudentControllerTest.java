package ru.hogwarts.school.controllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

//import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateStudent() {
        Student student = new Student(null, "Harry Potter", 17);
        ResponseEntity<Student> response = restTemplate.postForEntity("/student", student, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    public void testGetStudent() {
        Student student = new Student(null, "Harry Potter", 17);
        Student createdStudent = restTemplate.postForObject("/student", student, Student.class);

        ResponseEntity<Student> response = restTemplate.getForEntity("/student/" + createdStudent.getId(), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Harry Potter");
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student(null, "Harry Potter", 17);
        Student createdStudent = restTemplate.postForObject("/student", student, Student.class);

        createdStudent.setAge(18);
        restTemplate.put("/student/" + createdStudent.getId(), createdStudent);

        ResponseEntity<Student> response = restTemplate.getForEntity("/student/" + createdStudent.getId(), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAge()).isEqualTo(18);
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student(null, "Harry Potter", 17);
        Student createdStudent = restTemplate.postForObject("/student", student, Student.class);

        restTemplate.delete("/student/" + createdStudent.getId());

        ResponseEntity<Student> response = restTemplate.getForEntity("/student/" + createdStudent.getId(), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetAllStudents() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/student", Student[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetStudentsByAge() {
        Student student = new Student(null, "Harry Potter", 17);
        restTemplate.postForObject("/student", student, Student.class);

        ResponseEntity<Student[]> response = restTemplate.getForEntity("/student/age/17", Student[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    public void testGetStudentsByAgeRange() {
        Student student = new Student(null, "Harry Potter", 17);
        restTemplate.postForObject("/student", student, Student.class);

        ResponseEntity<Student[]> response = restTemplate.getForEntity("/student/between-ages?min=15&max=18", Student[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
    }
}
