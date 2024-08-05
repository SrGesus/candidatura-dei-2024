package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
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

    // Set of grades

    public Enrollment() {
    }

    public Enrollment(Candidate candidate, Studentship studentship, Boolean accepted) {
        this.candidate = candidate;
        this.studentship = studentship;
        this.candidateIstId = candidate.getIstId();
        this.studentshipId = studentship.getId();
        this.accepted = accepted;
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
}
