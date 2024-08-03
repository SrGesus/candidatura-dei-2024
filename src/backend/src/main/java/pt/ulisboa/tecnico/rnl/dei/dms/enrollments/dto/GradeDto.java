package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

public class GradeDto {
    
    private GradeParameterDto gradeParameter;

    private EnrollmentDto enrollment;

    private Float value;

    public GradeDto() {
    }

    public GradeDto(GradeParameterDto gradeParameter, EnrollmentDto enrollment, Float value) {
        this.gradeParameter = gradeParameter;
        this.enrollment = enrollment;
        this.value = value;
    }

    public GradeParameterDto getGradeParameter() {
        return gradeParameter;
    }

    public EnrollmentDto getEnrollment() {
        return enrollment;
    }

    public Float getValue() {
        return value;
    }

    public void setGradeParameter(GradeParameterDto gradeParameter) {
        this.gradeParameter = gradeParameter;
    }

    public void setEnrollment(EnrollmentDto enrollment) {
        this.enrollment = enrollment;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
