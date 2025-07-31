// web/TeacherController.java
package com.example.school.web;

import com.example.school.domain.School;
import com.example.school.domain.Teacher;
import com.example.school.repo.SchoolRepository;
import com.example.school.repo.TeacherRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherRepository repo;
    private final SchoolRepository schoolRepo;

    public TeacherController(TeacherRepository repo, SchoolRepository schoolRepo) {
        this.repo = repo;
        this.schoolRepo = schoolRepo;
    }

    @GetMapping
    public List<Teacher> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam Long schoolId, @RequestBody @Valid Teacher t) {
        School school = schoolRepo.findById(schoolId).orElse(null);
        if (school == null)
            return ResponseEntity.badRequest().body("Invalid schoolId");
        t.setSchool(school);
        Teacher saved = repo.save(t);
        return ResponseEntity.created(URI.create("/api/teachers/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Teacher t) {
        return repo.findById(id).map(existing -> {
            existing.setFirstName(t.getFirstName());
            existing.setLastName(t.getLastName());
            if (t.getSchool() != null)
                existing.setSchool(t.getSchool());
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

    // ✅ New endpoint to get teachers by schoolId
    @GetMapping("/bySchool")
    public List<Teacher> bySchool(@RequestParam Long schoolId) {
        return repo.findBySchoolId(schoolId);
    }
}
