package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "grade")
public class Grade {
    
    @EmbeddedId
    private GradeId gradeId;

    @MapsId("gradeParameterId")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private GradeParameter gradeParameter;

    @MapsId("enrollmentId")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Enrollment enrollment;

    @Column(nullable = false)
    private Double grade;

    public Grade() {
    }

    public Grade(GradeParameter gradeParameter, Enrollment enrollment, Double grade) {
        if (gradeParameter.getStudentship().getId() != enrollment.getStudentship().getId()) {
            throw new IllegalArgumentException("Grade parameter and enrollment must be from the same studentship");
        }
        this.gradeParameter = gradeParameter;
        this.enrollment = enrollment;
        this.grade = grade;
        // FIXME: Useless allocation of memory
        this.gradeId = new GradeId(gradeParameter.getId(), new EnrollmentId(enrollment.getCandidate().getIstId(), enrollment.getStudentship().getId()));
    }

    public GradeParameter getGradeParameter() {
        return gradeParameter;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGradeParameter(GradeParameter gradeParameter) {
        this.gradeParameter = gradeParameter;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Grade{" +
            "gradeParameter=" + gradeParameter +
            ", enrollment=" + enrollment +
            ", grade=" + grade +
            '}';
    }
}
