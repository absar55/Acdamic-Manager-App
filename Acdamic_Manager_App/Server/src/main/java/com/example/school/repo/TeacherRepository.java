package com.example.school.repo;

import com.example.school.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findBySchoolId(Long schoolId); // ✅ Add this
}
