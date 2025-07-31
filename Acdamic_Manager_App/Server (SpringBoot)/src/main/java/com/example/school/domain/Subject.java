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
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String code; // e.g., MATH101

    @NotBlank
    private String title; // e.g., Calculus I

    @ManyToOne(optional = false)
    @JoinColumn(name = "school_id")
    @JsonBackReference
    private School school;
}
