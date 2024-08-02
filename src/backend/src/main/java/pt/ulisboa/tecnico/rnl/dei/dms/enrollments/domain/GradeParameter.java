package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;

@Entity
@Table(name = "grade_parameter")
public class GradeParameter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "studentship_id", nullable = false)
    private Studentship studentship;

}
