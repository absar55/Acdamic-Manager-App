package com.example.school.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String address;

    @OneToMany(mappedBy = "school")
    private Set<Student> students;

    @OneToMany(mappedBy = "school")
    private Set<Teacher> teachers;

    @OneToMany(mappedBy = "school")
    @JsonManagedReference
    private Set<Subject> subjects;

    @OneToMany(mappedBy = "school")
    private Set<ClassSection> classes;
}
