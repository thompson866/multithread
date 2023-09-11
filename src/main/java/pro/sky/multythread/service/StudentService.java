package pro.sky.multythread.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.multythread.model.Faculty;
import pro.sky.multythread.model.Student;
import pro.sky.multythread.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        logger.info("call StudentService method");
        this.studentRepository = studentRepository;
    }


    public Student addStudent(Student student) {
        logger.info("call addStudent method");
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        logger.info("call getStudent method");
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.info("call edit Student method");
        return studentRepository.save(student);
    }

    public void removeStudent(long id) {
        logger.info("call remove Student method");
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        logger.info("call getStudentsByAge method");
        return studentRepository.findByAge(age);
    }


    public Collection<Student> getStudentsByAgeBetween(int minAge, int maxAge) {
        logger.info("call getStudentsByAgeBetween method");

        return studentRepository.findStudentsByAgeBetween(minAge, maxAge);
    }

    public Faculty getStudentFaculty(Long id) {
        logger.info("call getStudentFaculty method");
        return getStudent(id).getFaculty();

    }

//
//
//    public Integer getAllStudentsCount() {
//        logger.info("call getAllStudentsCount method");
//
//        return studentRepository.getAllStudentCount();
//    }
//
//    public Integer getStudentsAvgAge() {
//        logger.info("call getStudentsAvgAge method");
//
//        return studentRepository.getStudentsAverage();
//    }
//
//    public Collection<Student> getLastFiveStudentById() {
//        logger.info("call getLastFiveStudentById method");
//
//        return studentRepository.getLastFiveStudent();
//    }
//
//    public Collection<Student> getAll() {
//      logger.info("call getAll method");
//      return studentRepository.findAll();
//    }
}