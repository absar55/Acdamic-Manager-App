package com.example.school.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private LocalDate dateOfBirth;

    @ManyToOne(optional = false)
    @JoinColumn(name = "school_id")
    @JsonBackReference
    private School school;
}
