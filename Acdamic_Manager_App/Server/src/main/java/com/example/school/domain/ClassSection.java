package com.example.school.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name; // e.g., "MATH101 - A (Fall 2025)"

    @ManyToOne(optional = false)
    @JoinColumn(name = "school_id")
    @JsonBackReference("school-class")
    private School school;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id")
    @JsonBackReference("subject-class")
    private Subject subject;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id")
    @JsonBackReference("teacher-class")
    private Teacher teacher;
}
