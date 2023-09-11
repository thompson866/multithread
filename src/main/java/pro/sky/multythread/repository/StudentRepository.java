package pro.sky.multythread.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.multythread.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);
    Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge);

    @Query(value = "select count(*) from student", nativeQuery = true)
    int getAllStudentCount();

    @Query(value = "select average(age) from student", nativeQuery = true)
    int getStudentsAverage();

    @Query(value = "select *\n" + "from student\n" + "order by id desc\n" + "limit 5", nativeQuery = true)
    Collection<Student> getLastFiveStudent();
}

