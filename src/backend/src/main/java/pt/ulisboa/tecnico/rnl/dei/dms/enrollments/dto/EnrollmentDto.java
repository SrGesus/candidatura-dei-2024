package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

import java.util.Map;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;

public class EnrollmentDto {

    private Long candidateIstId;
    private Long studentshipId;
    private Boolean accepted;
    private Map<Long, Double> grades;

    public EnrollmentDto() {
    }

    public EnrollmentDto(Long candidateIstId, Long studentshipId, Boolean accepted, Map<Long, Double> grades) {
        this.candidateIstId = candidateIstId;
        this.studentshipId = studentshipId;
        this.accepted = accepted;
        this.grades = grades;
    }

    public EnrollmentDto(Enrollment enrollment) {
        this.candidateIstId = enrollment.getCandidate().getIstId();
        this.studentshipId = enrollment.getStudentship().getId();
        this.accepted = enrollment.getAccepted();
        this.grades = enrollment.getGrades().entrySet()
            .stream().collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue));
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

    public Map<Long, Double> getGrades() {
        return grades;
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

    public void setGrades(Map<Long, Double> grades) {
        this.grades = grades;
    }
}
