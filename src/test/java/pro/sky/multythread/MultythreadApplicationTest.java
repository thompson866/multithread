package pro.sky.multythread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.multythread.controller.AvatarController;
import pro.sky.multythread.controller.FacultyController;
import pro.sky.multythread.controller.StudentController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MultythreadApplicationTest {
    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private AvatarController avatarController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(studentController);
        Assertions.assertNotNull(facultyController);
        Assertions.assertNotNull(avatarController);
    }
}