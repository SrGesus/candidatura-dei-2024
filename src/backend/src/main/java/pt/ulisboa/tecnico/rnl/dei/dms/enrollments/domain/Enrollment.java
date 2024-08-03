package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import java.util.Map;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;

@Entity
@Table(name = "enrollment")
public class Enrollment {

    @EmbeddedId
    private EnrollmentId enrollmentId;

    @MapsId("candidateIstId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Candidate candidate;

    @MapsId("studentshipId")
    @ManyToOne(cascade = CascadeType.ALL)
    private Studentship studentship;

    @ElementCollection
    private Map<GradeParameter, Double> grades;

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

    public Map<GradeParameter, Double> getGrades() {
        return grades;
    }

    public void setCandidate(Candidate candidate) {
        this.enrollmentId.setCandidateIstId(candidate.getIstId());
        this.candidate = candidate;
    }

    public void setStudentship(Studentship studentship) {
        this.enrollmentId.setStudentshipId(studentship.getId());
        this.studentship = studentship;
    }

    public void setGrades(Map<GradeParameter, Double> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                ", candidate=" + candidate +
                ", studentship=" + studentship +
                '}';
    }

}
