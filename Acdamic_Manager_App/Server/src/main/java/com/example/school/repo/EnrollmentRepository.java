// src/main/java/com/example/school/repo/EnrollmentRepository.java
package com.example.school.repo;

import com.example.school.domain.Enrollment;
import com.example.school.domain.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
}
