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

    public CandidateDto getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDto candidate) {
        this.candidate = candidate;
    }

    public StudentshipDto getStudentship() {
        return studentship;
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