// domain/EnrollmentId.java
package com.example.school.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EnrollmentId implements Serializable {
    private Long studentId;
    private Long classSectionId;
}
