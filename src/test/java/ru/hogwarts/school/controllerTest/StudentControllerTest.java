package ru.hogwarts.school.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createStudentTest() throws Exception {
        Student student = new Student(1L, "Harry Potter", 11);
        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/student")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harry Potter"));
    }

    @Test
    public void getStudentTest() throws Exception {
        Student student = new Student(1L, "Harry Potter", 11);
        when(studentService.getStudent(anyLong())).thenReturn(student);

        mockMvc.perform(get("/student/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Harry Potter"));
    }

    @Test
    public void getAllStudentsTest() throws Exception {
        Student student = new Student(1L, "Harry Potter", 11);
        when(studentService.getAllStudents()).thenReturn(Collections.singletonList(student));

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harry Potter"));
    }

    @Test
    public void updateStudentTest() throws Exception {
        Student student = new Student(1L, "Harry Potter", 12);
        when(studentService.updateStudent(anyLong(), any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/student/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(12));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        when(studentService.deleteStudent(anyLong())).thenReturn(new Student());

        mockMvc.perform(delete("/student/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void findStudentsByAgeTest() throws Exception {
        Student student = new Student(1L, "Harry Potter", 11);
        when(studentService.findStudentsByAge(anyLong())).thenReturn(Collections.singletonList(student));

        mockMvc.perform(get("/student/age/{age}", 11L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harry Potter"));
    }

    @Test
    public void getStudentsByAgeRangeTest() throws Exception {
        Student student = new Student(1L, "Harry Potter", 11);
        when(studentService.getStudentsByAgeRange(anyLong(), anyLong())).thenReturn(Collections.singletonList(student));

        mockMvc.perform(get("/student/between-ages")
                        .param("min", "10")
                        .param("max", "12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Harry Potter"));
    }

    @Test
    public void getFacultyByStudentIdTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        Student student = new Student(1L, "Harry Potter", 11);
        student.setFaculty(faculty);
        when(studentService.getStudent(anyLong())).thenReturn(student);

        String responseString = mockMvc.perform(get("/student/{id}/faculty", 1L))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println("Response: " + responseString);

        mockMvc.perform(get("/student/{id}/faculty", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }
}

