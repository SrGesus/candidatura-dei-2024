package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity
@Table(name = "grade")
public class Grade {
    
    @EmbeddedId
    private GradeId gradeId;

    @MapsId("gradeParameterId")
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GradeParameter gradeParameter;

    @MapsId("enrollmentId")
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Enrollment enrollment;

    @Column(nullable = false)
    private Float grade;

    public Grade() {
    }

    public Grade(GradeParameter gradeParameter, Enrollment enrollment, Float grade) {
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

    public Float getGrade() {
        return grade;
    }

    public void setGradeParameter(GradeParameter gradeParameter) {
        this.gradeParameter = gradeParameter;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public void setGrade(Float grade) {
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
