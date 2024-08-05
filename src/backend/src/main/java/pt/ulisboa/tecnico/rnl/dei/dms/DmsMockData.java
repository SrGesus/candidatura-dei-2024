package pt.ulisboa.tecnico.rnl.dei.dms;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.CandidateService;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.EnrollmentService;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.StudentshipService;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.GradeParameterDto;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;

@Component
public class DmsMockData implements CommandLineRunner {

    @Autowired
    CandidateService candidateService;

    @Autowired
    StudentshipService studentshipService;

    @Autowired
    EnrollmentService enrollmentService;

    @Override
    public void run(String... args) throws Exception {
        // Candidates
        CandidateDto[] candidates = {
            new CandidateDto(100000L, "Maria Albertina", "mariaa@tecnico.ulisboa.pt"),
            new CandidateDto(100001L, "Vanessa Albertina", "candidaturas@vanessa.pt"),
            new CandidateDto(100002L, "Joaquim José", "joaquimjose@gmail.com"),
            new CandidateDto(100003L, "Maria José", "mase@portugal.gov"),
            new CandidateDto(100004L, "Chico da Tina", "chico@tina.da"),
            new CandidateDto(100005L, "Tó Zé", "gmail@mail.pt"),
            new CandidateDto(100006L, "Barnabé da Silva", "bdslv@ulisboa.pt"),
        };
        for (CandidateDto candidate : candidates) {
            candidateService.createCandidate(candidate);
        }

        // Studentships
        StudentshipDto[] studentships = {
            new StudentshipDto(LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), 598.80, 3, true, List.of(
                new GradeParameterDto("Currículo", 10.0),
                new GradeParameterDto("Carta de Motivação", 10.0)
            )),
            new StudentshipDto(LocalDate.of(2024, 8, 1), LocalDate.of(2025, 8, 1), 601.12, 2, true, List.of(
                new GradeParameterDto("Exercício Prático", 10.0),
                new GradeParameterDto("Entrevista", 6.0),
                new GradeParameterDto("Avaliação Curricular", 4.0)
            )),
            new StudentshipDto(LocalDate.of(2024, 8, 1), LocalDate.of(2025, 8, 1), 601.12, 2, true, List.of(
                new GradeParameterDto("Entrevista", 10.0),
                new GradeParameterDto("Avaliação Curricular", 10.0)
            )),
        };
        for (StudentshipDto studentship : studentships) {
            studentshipService.createStudentship(studentship);
        }

        // Enrollments
        EnrollmentDto[] enrollments = {
            new EnrollmentDto(100000L, 1L, false, Map.of(
                1L, 9.0
            )),
            new EnrollmentDto(100000L, 2L, false, Map.of()),
            new EnrollmentDto(100001L, 1L, false, Map.of()),
            new EnrollmentDto(100002L, 2L, false, Map.of(
                3L, 9.0,
                4L, 6.0,
                5L, 2.0
            )),
            new EnrollmentDto(100003L, 2L, false, Map.of(
                3L, 8.0,
                4L, 5.0,
                5L, 4.0
            )),
            new EnrollmentDto(100004L, 2L, false, Map.of(
                
            )),
            new EnrollmentDto(100005L, 2L, false, Map.of(
                
            )),
            new EnrollmentDto(100004L, 3L, false, Map.of()),
        };
        for (EnrollmentDto enrollment : enrollments) {
            enrollmentService.createEnrollment(enrollment);
        }
    }
    
}
