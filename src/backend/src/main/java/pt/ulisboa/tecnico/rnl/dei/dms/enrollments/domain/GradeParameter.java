package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;

@Entity
@Table(name = "grade_parameter")
public class GradeParameter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float weight;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Studentship studentship;

    public GradeParameter() {
    }

    public GradeParameter(String name, Float weight, Studentship studentship) {
        this.name = name;
        this.weight = weight;
        this.studentship = studentship;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getWeight() {
        return weight;
    }

    public Studentship getStudentship() {
        return studentship;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setStudentship(Studentship studentship) {
        this.studentship = studentship;
    }

    @Override
    public String toString() {
        return "GradeParameter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", studentship=" + studentship +
                '}';
    }
}
