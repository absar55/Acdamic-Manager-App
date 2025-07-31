// src/main/java/com/example/school/repo/StudentRepository.java
package com.example.school.repo;

import com.example.school.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
