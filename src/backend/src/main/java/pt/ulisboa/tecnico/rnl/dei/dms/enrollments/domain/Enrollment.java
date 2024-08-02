package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;

@Entity
@Table(name = "enrollment")
public class Enrollment {

    @EmbeddedId
    private EnrollmentId enrollmentId;

    @MapsId("candidateId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Candidate candidate;

    @MapsId("studentshipId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Studentship studentship;

    public Enrollment() {
    }

    public Enrollment(Candidate candidate, Studentship studentship) {
        this.enrollmentId = new EnrollmentId(candidate.getIstId(), studentship.getId());
        this.candidate = candidate;
        this.studentship = studentship;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Studentship getStudentship() {
        return studentship;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public void setStudentship(Studentship studentship) {
        this.studentship = studentship;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "candidate=" + candidate +
                ", studentship=" + studentship +
                '}';
    }

}
