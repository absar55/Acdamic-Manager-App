// web/ClassSectionController.java
package com.example.school.web;

import com.example.school.domain.*;
import com.example.school.repo.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassSectionController {
    private final ClassSectionRepository repo;
    private final SchoolRepository schoolRepo;
    private final SubjectRepository subjectRepo;
    private final TeacherRepository teacherRepo;

    public ClassSectionController(ClassSectionRepository repo,
            SchoolRepository schoolRepo,
            SubjectRepository subjectRepo,
            TeacherRepository teacherRepo) {
        this.repo = repo;
        this.schoolRepo = schoolRepo;
        this.subjectRepo = subjectRepo;
        this.teacherRepo = teacherRepo;
    }

    @GetMapping
    public List<ClassSection> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSection> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Create a class: pass schoolId, subjectId, teacherId as query params
    @PostMapping
    public ResponseEntity<?> create(@RequestParam Long schoolId,
            @RequestParam Long subjectId,
            @RequestParam Long teacherId,
            @RequestBody @Valid ClassSection c) {
        School school = schoolRepo.findById(schoolId).orElse(null);
        Subject subject = subjectRepo.findById(subjectId).orElse(null);
        Teacher teacher = teacherRepo.findById(teacherId).orElse(null);
        if (school == null || subject == null || teacher == null)
            return ResponseEntity.badRequest().body("Invalid IDs");
        c.setSchool(school);
        c.setSubject(subject);
        c.setTeacher(teacher);
        ClassSection saved = repo.save(c);
        return ResponseEntity.created(URI.create("/api/classes/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ClassSection c) {
        return repo.findById(id).map(existing -> {
            if (c.getName() != null)
                existing.setName(c.getName());
            if (c.getSchool() != null)
                existing.setSchool(c.getSchool());
            if (c.getSubject() != null)
                existing.setSubject(c.getSubject());
            if (c.getTeacher() != null)
                existing.setTeacher(c.getTeacher());
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
