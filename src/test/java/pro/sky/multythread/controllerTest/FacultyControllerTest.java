package pro.sky.multythread.controllerTest;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.multythread.controller.FacultyController;
import pro.sky.multythread.model.Faculty;
import pro.sky.multythread.model.Student;
import pro.sky.multythread.repository.FacultyRepository;
import pro.sky.multythread.repository.StudentRepository;
import pro.sky.multythread.service.FacultyService;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    FacultyRepository facultyRepository;

    @MockBean
    StudentRepository studentRepository;

    @SpyBean
    FacultyService facultyService;

    @InjectMocks
    FacultyController facultyController;

    @Test
    void getFacultyPositiveTest() throws Exception {
        long id = 1;
        String name = "Faculty";
        String color = "yellow";
        Faculty faculty = new Faculty(id, name, color);

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mvc.perform(get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        verify(facultyRepository, times(1)).findById(id);
    }

    @Test
    void getFacultyNegativeTest() throws Exception {
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.empty());

        mvc.perform(get("/faculty/42")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getFacultyByColorPositiveTest() throws Exception {
        Faculty faculty0 = new Faculty(1, "A", "yellow");
        Faculty faculty1 = new Faculty(2, "B", "yellow");

        List<Faculty> list = List.of(faculty0, faculty1);

        when(facultyRepository.findByColorLike(anyString())).thenReturn(list);

        mvc.perform(get("/faculty/color/yellow")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(faculty0.getId()))
                .andExpect(jsonPath("$.[0].name").value(faculty0.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty0.getColor()))
                .andExpect(jsonPath("$.[1].id").value(faculty1.getId()))
                .andExpect(jsonPath("$.[1].name").value(faculty1.getName()))
                .andExpect(jsonPath("$.[1].color").value(faculty1.getColor()));

    }

    @Test
    void getFacultyByColorNegativeTest() throws Exception {
        when(facultyRepository.findByColorLike(anyString())).thenReturn(Collections.emptyList());

        mvc.perform(get("/faculty/color/blue")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addFacultyTest() throws Exception {
        long id = 1;
        String name = "A";
        String color = "yellow";
        Faculty faculty = new Faculty(id, name, color);

        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mvc.perform(post("/faculty")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jo.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

    }

    @Test
    void editFacultyPositiveTest() throws Exception {
        long id = 1;
        String name = "A";
        String color = "yellow";
        Faculty faculty = new Faculty(id, name, color);

        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mvc.perform(put("/faculty")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jo.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

    }

    @Test
    void editFacultyNegativeTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(null);

        mvc.perform(put("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removeFacultyTest() throws Exception {
        mvc.perform(delete("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findFacultyByColorOrNameTest() throws Exception {
        Faculty faculty0 = new Faculty(1, "A", "yellow");
        Faculty faculry1 = new Faculty(2, "B", "yellow");

        List<Faculty> list = List.of(faculty0, faculry1);

        when(facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(list);

        mvc.perform(get("/faculty/color-or-name?param=yellow")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(faculty0.getId()))
                .andExpect(jsonPath("$.[0].name").value(faculty0.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty0.getColor()))
                .andExpect(jsonPath("$.[1].id").value(faculry1.getId()))
                .andExpect(jsonPath("$.[1].name").value(faculry1.getName()))
                .andExpect(jsonPath("$.[1].color").value(faculry1.getColor()));
    }

    @Test
    void getStudentsTest() throws Exception {
        Faculty f1 = new Faculty(1, "Faculty1", "yellow");
        Faculty f2 = new Faculty(2, "Faculty2", "red");
        Student s1 = new Student(1, "A", 10, f1);
        Student s2 = new Student(2, "B", 20, f1);
        Student s3 = new Student(3, "B", 20, f2);

        List<Student> list = List.of(s1, s2, s3);

        when(studentRepository.findAll()).thenReturn(list);

        mvc.perform(get("/faculty/students/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(s1.getId()))
                .andExpect(jsonPath("$.[0].name").value(s1.getName()))
                .andExpect(jsonPath("$.[0].age").value(s1.getAge()))
                .andExpect(jsonPath("$.[1].id").value(s2.getId()))
                .andExpect(jsonPath("$.[1].name").value(s2.getName()))
                .andExpect(jsonPath("$.[1].age").value(s2.getAge()));
    }
}

