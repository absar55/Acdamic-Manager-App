// web/EnrollmentController.java
package com.example.school.web;

import com.example.school.domain.ClassSection;
import com.example.school.domain.Enrollment;
import com.example.school.domain.EnrollmentId;
import com.example.school.domain.Student;
import com.example.school.repo.ClassSectionRepository;
import com.example.school.repo.EnrollmentRepository;
import com.example.school.repo.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository repo;
    private final StudentRepository studentRepo;
    private final ClassSectionRepository classRepo;

    public EnrollmentController(EnrollmentRepository repo,
            StudentRepository studentRepo,
            ClassSectionRepository classRepo) {
        this.repo = repo;
        this.studentRepo = studentRepo;
        this.classRepo = classRepo;
    }

    @GetMapping
    public List<Enrollment> all() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> enroll(@RequestParam Long studentId,
            @RequestParam Long classId) {

        Student student = studentRepo.findById(studentId).orElse(null);
        ClassSection clazz = classRepo.findById(classId).orElse(null);

        if (student == null) {
            return ResponseEntity.badRequest().body("Invalid studentId: " + studentId);
        }
        if (clazz == null) {
            return ResponseEntity.badRequest().body("Invalid classId: " + classId);
        }

        EnrollmentId id = new EnrollmentId(studentId, classId);
        if (repo.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body("Student " + studentId + " is already enrolled in class " + classId);
        }

        Enrollment e = Enrollment.builder()
                .id(id)
                .student(student)
                .classSection(clazz)
                .enrolledOn(LocalDate.now())
                .build();

        Enrollment saved = repo.save(e);
        return ResponseEntity
                .created(URI.create("/api/enrollments/" + studentId + "/" + classId))
                .body(saved);
    }

    @DeleteMapping
    public ResponseEntity<?> drop(@RequestParam Long studentId,
            @RequestParam Long classId) {
        EnrollmentId id = new EnrollmentId(studentId, classId);
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
