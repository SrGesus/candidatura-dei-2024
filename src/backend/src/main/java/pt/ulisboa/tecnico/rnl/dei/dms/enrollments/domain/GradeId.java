package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class GradeId {
    private Long gradeParameterId;
    
    @Embedded
    private EnrollmentId enrollmentId;

    public GradeId() {
    }

    public GradeId(Long gradeParameterId, EnrollmentId enrollmentId) {
        this.gradeParameterId = gradeParameterId;
        this.enrollmentId = enrollmentId;
    }

    public Long getGradeParameterId() {
        return gradeParameterId;
    }

    public EnrollmentId getEnrollmentId() {
        return enrollmentId;
    }

    public void setGradeParameterId(Long gradeParameterId) {
        this.gradeParameterId = gradeParameterId;
    }

    public void setEnrollmentId(EnrollmentId enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

}
