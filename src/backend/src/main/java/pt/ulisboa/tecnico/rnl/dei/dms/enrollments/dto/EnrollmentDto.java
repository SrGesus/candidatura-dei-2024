package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

import java.util.Map;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;

public class EnrollmentDto {

    private CandidateDto candidate;
    private StudentshipDto studentship;

    private Boolean accepted;
    private Map<Long, Double> grades;

    public EnrollmentDto() {
    }

    public EnrollmentDto(CandidateDto candidate, StudentshipDto studentship, Boolean accepted, Map<Long, Double> grades) {
        this.candidate = candidate;
        this.studentship = studentship;
        this.accepted = accepted;
        this.grades = grades;
    }

    public EnrollmentDto(Enrollment enrollment) {
        this.candidate = new CandidateDto(enrollment.getCandidate());
        this.studentship = new StudentshipDto(enrollment.getStudentship());
        this.accepted = enrollment.getAccepted();
        this.grades = enrollment.getGrades().entrySet()
            .stream().collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue));
    }

    public CandidateDto getCandidate() {
        return candidate;
    }

    public StudentshipDto getStudentship() {
        return studentship;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public Map<Long, Double> getGrades() {
        return grades;
    }

    public void setCandidate(CandidateDto candidate) {
        this.candidate = candidate;
    }

    public void setStudentship(StudentshipDto studentship) {
        this.studentship = studentship;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setGrades(Map<Long, Double> grades) {
        this.grades = grades;
    }
}
