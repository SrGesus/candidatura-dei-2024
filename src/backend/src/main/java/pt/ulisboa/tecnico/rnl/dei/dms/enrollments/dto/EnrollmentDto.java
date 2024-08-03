package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;

import java.util.Map;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;

public class EnrollmentDto {

    private CandidateDto candidate;
    private StudentshipDto studentship;
    private Map<String, Double> grades;

    public EnrollmentDto() {
    }

    public EnrollmentDto(Enrollment enrollment) {
        this.candidate = new CandidateDto(enrollment.getCandidate());
        this.studentship = new StudentshipDto(enrollment.getStudentship());
        this.grades = enrollment.getGrades() == null ? null : enrollment.getGrades().entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue));
    }

    public CandidateDto getCandidate() {
        return candidate;
    }
    
    public StudentshipDto getStudentship() {
        return studentship;
    }

    public Map<String, Double> getGrades() {
        return grades;
    }

    public void setCandidate(CandidateDto candidate) {
        this.candidate = candidate;
    }

    public void setStudentship(StudentshipDto studentship) {
        this.studentship = studentship;
    }

    public void setGrades(Map<String, Double> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "candidate=" + candidate +
                ", studentship=" + studentship +
                '}';
    }

}