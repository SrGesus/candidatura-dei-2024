package pt.ulisboa.tecnico.rnl.dei.dms;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.CandidateService;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.EnrollmentService;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.EnrollmentId;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.StudentshipService;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;

@Component
public class DmsMockData {
    @Autowired
    public DmsMockData(CandidateService candidateService, StudentshipService studentshipService, 
                       EnrollmentService enrollmentService) {
        // Candidates
        CandidateDto[] candidates = {
            new CandidateDto(Long.valueOf(802), "Joaquim Alberto", "joalberto43@portugal.gov"),
            new CandidateDto(Long.valueOf(803), "Maria Albertina", "mariaa@tecnico.ulisboa.pt"),
            new CandidateDto(Long.valueOf(809), "Vanessa Albertina", "candidaturas@vanessa.pt"),
            new CandidateDto(Long.valueOf(810), "Candidato 810", "email@mail.com"),
            new CandidateDto(Long.valueOf(811), "Candidato 811", "email@mail.com"),
            new CandidateDto(Long.valueOf(812), "Candidato 812", "email@mail.com"),
            new CandidateDto(Long.valueOf(813), "Candidato 813", "email@mail.com"),
            new CandidateDto(Long.valueOf(814), "Candidato 814", "email@mail.com"),
        };
        for (CandidateDto candidate : candidates) {
            candidateService.createCandidate(candidate);
        }
        // Studentships
        StudentshipDto[] studentships = {
            new StudentshipDto(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31), 598.80f, 3),
            new StudentshipDto(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31), 601.00f, 2),
        };
        for (StudentshipDto studentship : studentships) {
            studentshipService.createStudentship(studentship);
        }
        // Enrollments
        EnrollmentId[] enrollments = {
            new EnrollmentId(Long.valueOf(802), Long.valueOf(1)),
            new EnrollmentId(Long.valueOf(803), Long.valueOf(1)),
            new EnrollmentId(Long.valueOf(813), Long.valueOf(1)),
        };
        for (EnrollmentId enrollment : enrollments) {
            enrollmentService.createEnrollment(enrollment);
        }
    }
}
