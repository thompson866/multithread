package pro.sky.multythread.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.multythread.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByColorLike(String color);

    Collection<Faculty> findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(String color, String name);
}
