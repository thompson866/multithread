package pro.sky.multythread.service;

import org.springframework.stereotype.Service;
import pro.sky.multythread.model.Faculty;
import pro.sky.multythread.model.Student;
import pro.sky.multythread.repository.StudentRepository;
import java.util.Collection;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Integer getAllStudentsCount() {
        return studentRepository.getAllStudentCount();
    }

    public Integer getStudentsAvgAge() {
        return studentRepository.getStudentsAverage();
    }

    public Collection<Student> getLastFiveStudentById() {
        return studentRepository.getLastFiveStudent();
    }



    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void removeStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

//    public Collection<Student> getAll() {
//        return studentRepository.findAll();
//    }

    public Collection<Student> getStudentsByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge);
    }

    public Faculty getStudentFaculty(Long id) {
        return getStudent(id).getFaculty();
    }
}
