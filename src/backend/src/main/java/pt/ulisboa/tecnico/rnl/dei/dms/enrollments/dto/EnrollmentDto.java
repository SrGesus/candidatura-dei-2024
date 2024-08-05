package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;

public class EnrollmentDto {
    private Long candidateIstId;
    private Long studentshipId;
    private Boolean accepted;

    public EnrollmentDto() {
    }

    public EnrollmentDto(Long candidateIstId, Long studentshipId, Boolean accepted) {
        this.candidateIstId = candidateIstId;
        this.studentshipId = studentshipId;
        this.accepted = accepted;
    }

    public EnrollmentDto(Enrollment enrollment) {
        this.candidateIstId = enrollment.getCandidate().getIstId();
        this.studentshipId = enrollment.getStudentship().getId();
        this.accepted = enrollment.getAccepted();
    }

    public Long getCandidateIstId() {
        return candidateIstId;
    }

    public Long getStudentshipId() {
        return studentshipId;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setCandidateIstId(Long candidateIstId) {
        this.candidateIstId = candidateIstId;
    }

    public void setStudentshipId(Long studentshipId) {
        this.studentshipId = studentshipId;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
