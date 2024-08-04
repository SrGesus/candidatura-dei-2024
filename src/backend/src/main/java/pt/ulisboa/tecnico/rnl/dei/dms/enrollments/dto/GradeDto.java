package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Grade;

public class GradeDto {
    
    private Long gradeParameterId;
    private Long candidateIstId;
    private Long studentshipId;
    private Double grade;

    public GradeDto() {
    }

    public GradeDto(Long gradeParameterId, Long candidateIstId, Long studentshipId, Double value) {
        this.gradeParameterId = gradeParameterId;
        this.candidateIstId = candidateIstId;
        this.studentshipId = studentshipId;
        this.grade = value;
    }

    public GradeDto(Grade grade) {
        this.gradeParameterId = grade.getGradeParameter().getId();
        this.candidateIstId = grade.getEnrollment().getCandidate().getIstId();
        this.studentshipId = grade.getEnrollment().getStudentship().getId();
        this.grade = grade.getGrade();
    }

    public Long getGradeParameterId() {
        return gradeParameterId;
    }

    public Long getCandidateIstId() {
        return candidateIstId;
    }

    public Long getStudentshipId() {
        return studentshipId;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGradeParameterId(Long gradeParameterId) {
        this.gradeParameterId = gradeParameterId;
    }

    public void setCandidateIstId(Long candidateIstId) {
        this.candidateIstId = candidateIstId;
    }

    public void setStudentshipId(Long studentshipId) {
        this.studentshipId = studentshipId;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
