// src/main/java/com/example/school/repo/SchoolRepository.java
package com.example.school.repo;

import com.example.school.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
