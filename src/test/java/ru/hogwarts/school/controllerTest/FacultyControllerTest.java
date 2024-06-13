package ru.hogwarts.school.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createFacultyTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }

    @Test
    public void getFacultyTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        when(facultyService.getFaculty(anyLong())).thenReturn(faculty);

        mockMvc.perform(get("/faculty/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }

    @Test
    public void getAllFacultiesTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        when(facultyService.getAllFaculties()).thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gryffindor"));
    }

    @Test
    public void updateFacultyTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Gold");
        when(facultyService.updateFaculty(anyLong(), any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(put("/faculty/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Gold"));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        when(facultyService.deleteFaculty(anyLong())).thenReturn(new Faculty());

        mockMvc.perform(delete("/faculty/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void findFacultiesByColorTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        when(facultyService.findByColor(any(String.class))).thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(get("/faculty/color/{color}", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gryffindor"));
    }

    @Test
    public void searchFacultiesTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        when(facultyService.searchFaculties(any(String.class), any(String.class))).thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(get("/faculty/search")
                        .param("name", "Gryffindor")
                        .param("color", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gryffindor"));
    }

    @Test
    public void getStudentsByFacultyIdTest() throws Exception {
        Student student = new Student(1L, "Harry Potter", 11);
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        faculty.setStudents(Collections.singletonList(student));
        when(facultyService.getFaculty(anyLong())).thenReturn(faculty);

        String responseString = mockMvc.perform(get("/faculty/{id}/students", 1L))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println("Response: " + responseString);

        mockMvc.perform(get("/faculty/{id}/students", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harry Potter"));
    }
}
