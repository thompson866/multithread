package pro.sky.multythread.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.multythread.model.Student;
import pro.sky.multythread.repository.StudentRepository;
import pro.sky.multythread.service.StudentService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repositoryMock;

	@InjectMocks
	StudentService out;

	@Test
	void addStudentTest() {
        Student student = new Student(1, "harry", 10, null);
		when(repositoryMock.save(student)).thenReturn(student);
		assertEquals(student, out.addStudent(student));
	}

	@Test
	void getStudentPositiveTest() {
        Student student = new Student(1L, "harry", 10, null);
        when(repositoryMock.findById(1L)).thenReturn(Optional.of(student));
        assertEquals(student, out.getStudent(1));
	}

	@Test
	void getStudentNegativeTest() {
        when(repositoryMock.findById(4L)).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, () -> out.getStudent(4));
	}

	@Test
	void editStudentTest() {
		Student student = new Student(1, "ron", 11, null);
        when(repositoryMock.save(student)).thenReturn(student);
		assertEquals(student, out.editStudent(student));
	}
	@Test
	void getStudentsByAgePositiveTest() {
		Student student = new Student(4,"harry", 11, null);
        when(repositoryMock.findByAgeLessThan(31)).thenReturn(List.of(student));
		assertIterableEquals(List.of(student), out.getStudentsByAge(31));
	}

	@Test
	void getStudentsByAgeNegativeTest() {
		List<Student> test = Collections.emptyList();
        when(repositoryMock.findByAgeLessThan(31)).thenReturn(test);
		assertIterableEquals(test, out.getStudentsByAge(31));
	}
 }
