// web/SchoolController.java
package com.example.school.web;

import com.example.school.domain.School;
import com.example.school.repo.SchoolRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {
    private final SchoolRepository repo;

    public SchoolController(SchoolRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<School> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<School> create(@RequestBody @Valid School s) {
        School saved = repo.save(s);
        return ResponseEntity.created(URI.create("/api/schools/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<School> update(@PathVariable Long id, @RequestBody @Valid School s) {
        return repo.findById(id).map(existing -> {
            existing.setName(s.getName());
            existing.setAddress(s.getAddress());
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
