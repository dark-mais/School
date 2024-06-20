package ru.hogwarts.school.controllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest2 {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    public void setup() {
        // Очистка базы данных перед каждым тестом
        facultyRepository.deleteAll();

        // Создание факультетов для тестирования
        Faculty faculty1 = new Faculty(null, "Gryffindor", "Red");
        Faculty faculty2 = new Faculty(null, "Slytherin", "Green");
        facultyRepository.save(faculty1);
        facultyRepository.save(faculty2);
    }

    @Test
    public void createFacultyTest() {
        Faculty faculty = new Faculty(null, "Hufflepuff", "Yellow");
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty", faculty, Faculty.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getName()).isEqualTo("Hufflepuff");
    }

    @Test
    public void getFacultyTest() {
        Faculty faculty = facultyRepository.findAll().iterator().next();
        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/{id}", Faculty.class, faculty.getId());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getId()).isEqualTo(faculty.getId());
    }

    @Test
    public void getAllFacultiesTest() {
        ResponseEntity<Collection> response = restTemplate.getForEntity("/faculty", Collection.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void updateFacultyTest() {
        Faculty faculty = facultyRepository.findAll().iterator().next();
        faculty.setColor("Gold");
        HttpEntity<Faculty> requestUpdate = new HttpEntity<>(faculty);
        ResponseEntity<Faculty> response = restTemplate.exchange("/faculty/{id}", HttpMethod.PUT, requestUpdate, Faculty.class, faculty.getId());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getColor()).isEqualTo("Gold");
    }

    @Test
    public void deleteFacultyTest() {
        Faculty faculty = facultyRepository.findAll().iterator().next();
        ResponseEntity<Faculty> response = restTemplate.exchange("/faculty/{id}", HttpMethod.DELETE, null, Faculty.class, faculty.getId());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void findFacultiesByColorTest() {
        ResponseEntity<Collection> response = restTemplate.getForEntity("/faculty/color/{color}", Collection.class, "Red");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void searchFacultiesTest() {
        ResponseEntity<Collection> response = restTemplate.getForEntity("/faculty/search?name={name}&color={color}", Collection.class, "Gryffindor", "Red");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void getStudentsByFacultyIdTest() {
        Faculty faculty = facultyRepository.findAll().iterator().next();
        // Предполагаем, что студенты уже связаны с факультетом в базе данных
        ResponseEntity<Collection> response = restTemplate.getForEntity("/faculty/{id}/students", Collection.class, faculty.getId());
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
    }
}
