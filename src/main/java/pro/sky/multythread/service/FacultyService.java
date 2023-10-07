package pro.sky.multythread.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.multythread.exceptions.NoElementException;
import pro.sky.multythread.model.Faculty;
import pro.sky.multythread.model.Student;
import pro.sky.multythread.repository.FacultyRepository;
import pro.sky.multythread.repository.StudentRepository;

import java.util.*;
import java.util.stream.Stream;

@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);



    private final FacultyRepository facultyRepository;


    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {

        logger.info("call FacultyService method");
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("call addFaculty method");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id) {
        logger.info("call getFaculty method");
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("call edit method");
        return facultyRepository.save(faculty);
    }

    public void removeFaculty(long id) {
        logger.info("call removeFaculty method");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.info("call getFaculty method");
        return facultyRepository.findByColorLike(color);
    }


    public Collection<Faculty> getFacultiesByColorOrName(String param) {
        logger.info("call getFacultiesByColorOrName method");
        return facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(param, param);
    }

    public Collection<Student> getStudents(Long facultyId) {
        logger.info("call getStudents method");
        return getFaculty(facultyId).getStudents();
    }

    public String getLongFaculty() {
        Optional<String> longestName = facultyRepository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length));
        return longestName.orElseThrow(NoElementException::new);
    }

    public int getReturnNumber() {
        return Stream.iterate(1, a -> a + 1).limit(1_000_000).parallel().reduce(0, Integer::sum);
    }
}
//
//    public Collection<Faculty> getAll() {
//        return facultyRepository.findAll();
//    }