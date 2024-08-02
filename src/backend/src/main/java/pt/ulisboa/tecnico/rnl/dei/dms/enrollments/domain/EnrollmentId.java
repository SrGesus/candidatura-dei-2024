package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class EnrollmentId implements Serializable {

    private Long candidateId;
    
    private Long studentshipId;


    public EnrollmentId() {
    }

    public EnrollmentId(Long candidateId, Long studentshipId) {
        this.candidateId = candidateId;
        this.studentshipId = studentshipId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public Long getStudentshipId() {
        return studentshipId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public void setStudentshipId(Long studentshipId) {
        this.studentshipId = studentshipId;
    }

    @Override
    public String toString() {
        return "EnrollmentKeyDto{" +
                "candidateId=" + candidateId +
                ", studentshipId=" + studentshipId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EnrollmentId enrollmentId)
            return candidateId.equals(enrollmentId.candidateId) 
                && studentshipId.equals(enrollmentId.studentshipId);
        else if (o instanceof Enrollment enrollment)
            return candidateId.equals(enrollment.getCandidate().getIstId()) 
                && studentshipId.equals(enrollment.getStudentship().getId());
        else
            return false;
    }

    @Override
    public int hashCode() {
        // Good enough
        return candidateId.hashCode() + studentshipId.hashCode();
    }
}
