package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import java.util.Map;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.GradeParameter;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;

@Entity
@Table(name = "enrollment", indexes = {
    @Index(name = "enrollment_candidate_ist_id_idx", columnList = "candidateIstId"),
    @Index(name = "enrollment_studentship_id_idx", columnList = "studentshipId")
})
@IdClass(EnrollmentId.class)
public class Enrollment {
    
    @Id
    @Column(name = "candidate_ist_id")
    private Long candidateIstId;

    @Id
    @Column(name = "studentship_id")
    private Long studentshipId;

    @MapsId
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Candidate candidate;

    @MapsId
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Studentship studentship;

    @Column(nullable = false)
    private Boolean accepted;

    @ElementCollection
    @CollectionTable(name = "grade", joinColumns = {
        // On Delete Cascade is not needed here because JPA will delete the grades when the Enrollment is deleted
        @JoinColumn(name = "enrollment_candidate_ist_id", referencedColumnName = "candidate_ist_id"),
        @JoinColumn(name = "enrollment_studentship_id", referencedColumnName = "studentship_id")
    })
    @MapKeyJoinColumn(name = "grade_parameter_id", foreignKey = @ForeignKey(
        name = "grade_grade_parameter_id_fkey",
        // JPA doesn't seem to allow a better way to make On Delete Cascade work for grade_parameters
        // but shouldn't be a big problem in terms of maintenance because everything is in the same place
        foreignKeyDefinition = "FOREIGN KEY (grade_parameter_id) REFERENCES grade_parameter(id) ON DELETE CASCADE"
    ))
    @Column(name = "grade", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<GradeParameter, Double> grades;

    public Enrollment() {
    }

    public Enrollment(Candidate candidate, Studentship studentship, Boolean accepted, Map<GradeParameter, Double> grades) {
        this.candidate = candidate;
        this.studentship = studentship;
        this.candidateIstId = candidate.getIstId();
        this.studentshipId = studentship.getId();
        this.accepted = accepted;
        this.grades = grades;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Studentship getStudentship() {
        return studentship;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public Map<GradeParameter, Double> getGrades() {
        return grades;
    }

    public void setCandidate(Candidate candidate) {
        this.candidateIstId = candidate.getIstId();
        this.candidate = candidate;
    }

    public void setStudentship(Studentship studentship) {
        this.studentshipId = studentship.getId();
        this.studentship = studentship;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setGrades(Map<GradeParameter, Double> grades) {
        this.grades = grades;
    }
}
