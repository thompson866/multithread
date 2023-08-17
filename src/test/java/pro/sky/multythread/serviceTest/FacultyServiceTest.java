package pro.sky.multythread.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.multythread.model.Faculty;
import pro.sky.multythread.repository.FacultyRepository;
import pro.sky.multythread.service.FacultyService;


import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository repositoryMock;

    @InjectMocks
    FacultyService out;

    @Test
    void addFacultyTest() {
        Faculty faculty = new Faculty(4,"DDD", "green");
        when(repositoryMock.save(faculty)).thenReturn(faculty);
        assertEquals(faculty, out.addFaculty(faculty));
    }

    @Test
    void getFacultyPositiveTest() {
        Faculty faculty = new Faculty(4,"DDD", "green");
        when(repositoryMock.findById(4L)).thenReturn(Optional.of(faculty));
        assertEquals(faculty, out.getFaculty(4));
    }

    @Test
    void getFacultyNegativeTest() {
        when(repositoryMock.findById(4L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> out.getFaculty(4));
    }

    @Test
    void editFacultyTest() {
        Faculty faculty = new Faculty(3, "CCC", "black");
        when(repositoryMock.save(faculty)).thenReturn(faculty);
        assertEquals(faculty, out.editFaculty(faculty));
    }


    @Test
    void getFacultiesByColorPositiveTest() {
        Faculty f = new Faculty(4,"DDD", "green");
        when(repositoryMock.findByColorLike("green")).thenReturn(List.of(f));
        assertIterableEquals(List.of(f), out.getFacultiesByColor("green"));
    }

    @Test
    void getFacultiesByColorNegativeTest() {
        List<Faculty> test = Collections.emptyList();
        when(repositoryMock.findByColorLike("black")).thenReturn(test);
        assertIterableEquals(test, out.getFacultiesByColor("black"));
    }

}
