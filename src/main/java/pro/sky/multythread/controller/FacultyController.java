package pro.sky.multythread.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.multythread.model.Faculty;
import pro.sky.multythread.model.Student;
import pro.sky.multythread.service.FacultyService;


import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long facultyId) {
        Faculty foundFaculty = facultyService.getFaculty(facultyId);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @GetMapping("/color/{facultyColor}")
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(@PathVariable String facultyColor) {
        Collection<Faculty> result = facultyService.getFacultiesByColor(facultyColor);
        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty addedFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(addedFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editedFaculty = facultyService.editFaculty(faculty);
        if (editedFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(editedFaculty);
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity<?> removeFaculty(@PathVariable Long facultyId) {
        facultyService.removeFaculty(facultyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color-or-name")
    public Collection<Faculty> findByColorOrName(@RequestParam String param) {
        return facultyService.getFacultiesByColorOrName(param);
    }

    @GetMapping("/students/{facultyId}")
    public Collection<Student> getStudents(@PathVariable Long facultyId) {
        return facultyService.getStudents(facultyId);
    }

    @GetMapping("longName")
    public String getLongestFacultyName() {
        return facultyService.getLongFaculty();
    }

    @GetMapping("getNumder")
    public int getReturnNumber(){
        return facultyService.getReturnNumber();
    }
}
