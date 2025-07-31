// src/main/java/com/example/school/repo/ClassSectionRepository.java
package com.example.school.repo;

import com.example.school.domain.ClassSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassSectionRepository extends JpaRepository<ClassSection, Long> {
}
