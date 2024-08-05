package pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.GradeParameterDto;

@Entity
@Table(name = "grade_parameter")
public class GradeParameter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double weight;

    @ManyToOne(optional = false)
    @PrimaryKeyJoinColumn(name = "studentship_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Studentship studentship;

    public GradeParameter() {
    }

    public GradeParameter(Long id, String name, Double weight, Studentship studentship) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.studentship = studentship;
    }

    public GradeParameter(GradeParameterDto gradeParameterDto, Studentship studentship) {
        this.id = gradeParameterDto.getId();
        this.name = gradeParameterDto.getName();
        this.weight = gradeParameterDto.getWeight();
        this.studentship = studentship;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getWeight() {
        return weight;
    }

    public Studentship getStudentship() {
        return studentship;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GradeParameter && ((GradeParameter) o).getId().equals(this.id);
    }
}
