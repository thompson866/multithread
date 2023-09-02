package pro.sky.multythread.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.multythread.exceptions.BadParamsException;
import pro.sky.multythread.model.Faculty;
import pro.sky.multythread.model.Student;
import pro.sky.multythread.service.StudentService;


import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }




    @GetMapping("get/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PostMapping("add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student addedStudent = studentService.addStudent(student);
        return ResponseEntity.ok(addedStudent);
    }

    @PutMapping("update")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editedStudent = studentService.editStudent(student);
        if (editedStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(editedStudent);
    }


    @DeleteMapping("remove/{id}")
    public void removeStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
    }

    @GetMapping(value = "get", params = "age")
    public Collection<Student> getStudentsByAge(@RequestParam (required = false) Integer age) {
        checkParametersForNull(age);
        return studentService.getStudentsByAge(age);
    }

    @GetMapping(value = "get", params = {"minAge", "maxAge"})
    public Collection<Student> getStudentsByAgeBetween(@RequestParam (required = false) Integer minAge, @RequestParam(required = false) Integer maxAge) {
        checkParametersForNull(minAge, maxAge);
        return studentService.getStudentsByAgeBetween(minAge, maxAge);
    }

    @GetMapping("{id}/getFaculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }




    private void checkParametersForNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new BadParamsException();
            }
        }
    }
}
