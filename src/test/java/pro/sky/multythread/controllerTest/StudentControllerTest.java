package pro.sky.multythread.controllerTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.multythread.model.Faculty;
import pro.sky.multythread.model.Student;
import pro.sky.multythread.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private final Random random = new Random();
    int counter = 0;

    StudentControllerTest() {
    }

    @AfterEach
    public void clearRepository() {
        studentRepository.deleteAll();
        counter = 0;
    }

    private Student createTestStudent(Integer age) {
        Student studentTest = new Student();
        studentTest.setAge((age == null) ? random.nextInt(100) : age);
        studentTest.setName("TestStudent" + counter++);
        return studentTest;
    }

    @Test
    void addStudentTest() {
        Student studentIn = createTestStudent(null);
        Student studentOut = restTemplate.postForObject("http://localhost:" + port + "/student/add", studentIn, Student.class);
        assertEquals(studentIn.getName(), studentOut.getName());
        assertEquals(studentIn.getAge(), studentOut.getAge());
        assertNotNull(studentOut.getId());
    }

    @Test
    void getStudentTest() {
        Student studentIn = createTestStudent(null);
        Student studentExpected = restTemplate.postForObject("http://localhost:" + port + "/student/add", studentIn, Student.class);
        Student studentActual = restTemplate.getForObject("http://localhost:" + port + "/student/get/" + studentExpected.getId(), Student.class);
        assertEquals(studentExpected.getName(), studentActual.getName());
        assertEquals(studentExpected.getAge(), studentActual.getAge());
        assertEquals(studentExpected.getId(), studentActual.getId());
    }

    @Test
    void updateStudentTest() {
        Student studentIn = restTemplate.postForObject("http://localhost:" + port + "/student/add", createTestStudent(null), Student.class);
        Student expectedStudent = new Student();
        expectedStudent.setName("updatedStudent");
        expectedStudent.setAge(10);
        expectedStudent.setId(studentIn.getId());
        ResponseEntity<Student> responseEntity = restTemplate.exchange("http://localhost:" + port + "/student/update", HttpMethod.PUT, new HttpEntity<>(expectedStudent), Student.class);
        Student studentOut = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedStudent.getName(), studentOut.getName());
        assertEquals(expectedStudent.getAge(), studentOut.getAge());
        assertEquals(expectedStudent.getId(), studentOut.getId());
    }

    @Test
    void removeStudent() {
        Student studentIn = restTemplate.postForObject("http://localhost:" + port + "/student/add", createTestStudent(null), Student.class);
        ResponseEntity<Student> responseEntity = restTemplate.exchange("http://localhost:" + port + "/student/remove/" + studentIn.getId(), HttpMethod.DELETE, new HttpEntity<>(studentIn.getId()), Student.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getStudentsByAgeTest() {
        Collection<Student> studentsExpected = new ArrayList<>();
        int age = 11;
        restTemplate.postForObject("http://localhost:" + port + "/student/add", createTestStudent(11), Student.class);
        studentsExpected.add(restTemplate.postForObject("http://localhost:" + port + "/student/add", createTestStudent(age), Student.class));
        studentsExpected.add(restTemplate.postForObject("http://localhost:" + port + "/student/add", createTestStudent(age), Student.class));
        ResponseEntity<List<Student>> studentsActual = restTemplate.exchange(
                "http://localhost:" + port + "/student/get?age=" + age,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(studentsExpected, studentsActual.getBody());
    }

    @Test
    void findByAgeBetweenTest() {
        Collection<Student> studentsExpected = new ArrayList<>();
        studentsExpected.add(restTemplate.postForObject("http://localhost:" + port + "/student/add", createTestStudent(11), Student.class));
        studentsExpected.add(restTemplate.postForObject("http://localhost:" + port + "/student/add", createTestStudent(14), Student.class));
        restTemplate.postForObject("http://localhost:" + port + "/student/add", createTestStudent(16), Student.class);
        ResponseEntity<List<Student>> studentsActual = restTemplate.exchange(
                "http://localhost:" + port + "/student/get?minAge=" + 10 + "&maxAge=" + 14,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(studentsExpected, studentsActual.getBody());
    }

    @Test
    void getStudentFacultyTest() {
        Student studentIn = createTestStudent(null);
        Faculty faculty = new Faculty();
        faculty.setName("testFaculty");
        faculty.setColor("testColor");
        Faculty facultyOut = restTemplate.postForObject("http://localhost:" + port + "/faculty/add", faculty, Faculty.class);
        studentIn.setFaculty(facultyOut);
        Student studentOut = restTemplate.postForObject("http://localhost:" + port + "/student/add", studentIn, Student.class);
        Faculty facultyActual = restTemplate.getForObject("http://localhost:" + port + "/student/" + studentOut.getId() + "/getFaculty", Faculty.class);
        assertEquals(studentIn.getFaculty(), facultyActual);
    }
}
