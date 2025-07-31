// web/StudentController.java
package com.example.school.web;

import com.example.school.domain.School;
import com.example.school.domain.Student;
import com.example.school.repo.SchoolRepository;
import com.example.school.repo.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentRepository repo;
    private final SchoolRepository schoolRepo;

    public StudentController(StudentRepository repo, SchoolRepository schoolRepo) {
        this.repo = repo;
        this.schoolRepo = schoolRepo;
    }

    @GetMapping
    public List<Student> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam Long schoolId, @RequestBody @Valid Student s) {
        School school = schoolRepo.findById(schoolId).orElse(null);
        if (school == null)
            return ResponseEntity.badRequest().body("Invalid schoolId");
        s.setSchool(school);
        Student saved = repo.save(s);
        return ResponseEntity.created(URI.create("/api/students/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Student s) {
        return repo.findById(id).map(existing -> {
            existing.setFirstName(s.getFirstName());
            existing.setLastName(s.getLastName());
            existing.setDateOfBirth(s.getDateOfBirth());
            if (s.getSchool() != null)
                existing.setSchool(s.getSchool());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id))
            return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
