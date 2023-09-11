package pro.sky.multythread.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.multythread.model.Faculty;
import pro.sky.multythread.model.Student;
import pro.sky.multythread.repository.FacultyRepository;
import pro.sky.multythread.repository.StudentRepository;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
}
//
//    public Collection<Faculty> getAll() {
//        return facultyRepository.findAll();
//    }