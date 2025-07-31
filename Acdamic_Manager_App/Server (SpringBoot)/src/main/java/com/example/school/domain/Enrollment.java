// domain/Enrollment.java
package com.example.school.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    @EmbeddedId
    private EnrollmentId id;

    @ManyToOne(optional = false)
    @MapsId("studentId")
    private Student student;

    @ManyToOne(optional = false)
    @MapsId("classSectionId")
    private ClassSection classSection;

    private LocalDate enrolledOn = LocalDate.now();

    // room for grade/status/etc.
}
