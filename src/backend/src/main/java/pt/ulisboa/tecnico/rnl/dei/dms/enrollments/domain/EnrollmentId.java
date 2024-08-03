package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class EnrollmentId implements Serializable {

    private Long candidateIstId;
    
    private Long studentshipId;

    public EnrollmentId() {
    }

    public EnrollmentId(Long candidateId, Long studentshipId) {
        this.candidateIstId = candidateId;
        this.studentshipId = studentshipId;
    }

    public Long getCandidateIstId() {
        return candidateIstId;
    }

    public Long getStudentshipId() {
        return studentshipId;
    }

    public void setCandidateIstId(Long candidateId) {
        this.candidateIstId = candidateId;
    }

    public void setStudentshipId(Long studentshipId) {
        this.studentshipId = studentshipId;
    }

    @Override
    public String toString() {
        return "EnrollmentKeyDto{" +
                "candidateIstId=" + candidateIstId +
                ", studentshipId=" + studentshipId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EnrollmentId enrollmentId)
            return candidateIstId.equals(enrollmentId.candidateIstId) 
                && studentshipId.equals(enrollmentId.studentshipId);
        else if (o instanceof Enrollment enrollment)
            return candidateIstId.equals(enrollment.getCandidate().getIstId()) 
                && studentshipId.equals(enrollment.getStudentship().getId());
        else
            return false;
    }

    @Override
    public int hashCode() {
        // Good enough
        return candidateIstId.hashCode() + studentshipId.hashCode();
    }
}
