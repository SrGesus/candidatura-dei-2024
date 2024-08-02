package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;

public class EnrollmentDto {

    private CandidateDto candidate;
    private StudentshipDto studentship;

    public EnrollmentDto() {
    }

    public EnrollmentDto(Enrollment enrollment) {
        this.candidate = new CandidateDto(enrollment.getCandidate());
        this.studentship = new StudentshipDto(enrollment.getStudentship());
    }

    public CandidateDto getCandidateDto() {
        return candidate;
    }

    public void setCandidateDto(CandidateDto candidate) {
        this.candidate = candidate;
    }

    public StudentshipDto getStudentshipDto() {
        return studentship;
    }

    public void setStudentshipDto(StudentshipDto studentship) {
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