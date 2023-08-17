package pro.sky.multythread.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.multythread.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeLessThan(int age);

    Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge);
}
