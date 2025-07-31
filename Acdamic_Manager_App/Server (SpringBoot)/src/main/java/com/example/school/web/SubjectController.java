// web/SubjectController.java
package com.example.school.web;

import com.example.school.domain.School;
import com.example.school.domain.Subject;
import com.example.school.repo.SchoolRepository;
import com.example.school.repo.SubjectRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectRepository repo;
    private final SchoolRepository schoolRepo;

    public SubjectController(SubjectRepository repo, SchoolRepository schoolRepo) {
        this.repo = repo;
        this.schoolRepo = schoolRepo;
    }

    @GetMapping
    public List<Subject> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ✅ New endpoint: Get subjects by schoolId
    @GetMapping("/bySchool")
    public ResponseEntity<List<Subject>> getSubjectsBySchool(@RequestParam Long schoolId) {
        List<Subject> subjects = repo.findBySchoolId(schoolId);
        return ResponseEntity.ok(subjects);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestParam Long schoolId, @RequestBody @Valid Subject s) {
        School school = schoolRepo.findById(schoolId).orElse(null);
        if (school == null)
            return ResponseEntity.badRequest().body("Invalid schoolId");
        s.setSchool(school);
        Subject saved = repo.save(s);
        return ResponseEntity.created(URI.create("/api/subjects/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Subject s) {
        return repo.findById(id).map(existing -> {
            existing.setCode(s.getCode());
            existing.setTitle(s.getTitle());
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
