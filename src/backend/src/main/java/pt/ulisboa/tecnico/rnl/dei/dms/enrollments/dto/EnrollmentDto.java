package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;

public class EnrollmentDto {

    private CandidateDto candidate;
    private StudentshipDto studentship;
    private Map<Long, Float> grades;

    public EnrollmentDto() {
    }

    public EnrollmentDto(Enrollment enrollment) {
        this.candidate = new CandidateDto(enrollment.getCandidate());
        this.studentship = new StudentshipDto(enrollment.getStudentship());
        this.grades = enrollment.getGrades() == null ? null : enrollment.getGrades().stream()
            .map(grade -> Map.entry(grade.getGradeParameter().getId(), grade.getGrade()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public CandidateDto getCandidate() {
        return candidate;
    }
    
    public StudentshipDto getStudentship() {
        return studentship;
    }

    public Map<Long, Float> getGrades() {
        return grades;
    }

    public void setCandidate(CandidateDto candidate) {
        this.candidate = candidate;
    }

    public void setStudentship(StudentshipDto studentship) {
        this.studentship = studentship;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "candidate=" + candidate +
                ", studentship=" + studentship +
                '}';
    }

}