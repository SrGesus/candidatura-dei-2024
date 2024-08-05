package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import java.io.Serializable;

// Id class for Enrollment
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof EnrollmentId e) {
            return candidateIstId.equals(e.candidateIstId) && studentshipId.equals(e.studentshipId);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        // Good enough
        return candidateIstId.hashCode() + studentshipId.hashCode();
    }
}
