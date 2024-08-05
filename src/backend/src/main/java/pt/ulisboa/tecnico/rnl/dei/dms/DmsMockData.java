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
            new StudentshipDto(LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), 598.80, 3, false, List.of(
                new GradeParameterDto("Exercício Prático", 10.0),
                new GradeParameterDto("Avaliação Curricular", 10.0)
            )),
        };
        for (int i = 0; i < studentships.length; i++) {
            studentships[i].setId(i + 1L);
        }
        for (StudentshipDto studentship : studentships) {
            studentshipService.createStudentship(studentship);
        }

        // Enrollments
        EnrollmentDto[] enrollments = {
            new EnrollmentDto(candidates[0], studentships[0], false, Map.of(
                1L, 9.0
            )),
            new EnrollmentDto(candidates[0], studentships[1], false, Map.of()),
            new EnrollmentDto(candidates[1], studentships[0], false, Map.of()),
            new EnrollmentDto(candidates[2], studentships[1], false, Map.of(
                3L, 9.0,
                4L, 6.0,
                5L, 2.0
            )),
            new EnrollmentDto(candidates[3], studentships[1], false, Map.of(
                3L, 8.0,
                4L, 5.0,
                5L, 4.0
            )),
            new EnrollmentDto(candidates[4], studentships[1], false, Map.of(
                
            )),
            new EnrollmentDto(candidates[5], studentships[1], false, Map.of(
                
            )),
            new EnrollmentDto(candidates[4], studentships[2], false, Map.of()),
            new EnrollmentDto(candidates[5], studentships[2], false, Map.of()),
            new EnrollmentDto(candidates[0], studentships[3], false, Map.of(
                8L, 3.0,
                9L, 7.0
            )),
            new EnrollmentDto(candidates[1], studentships[3], true, Map.of(
                8L, 5.0,
                9L, 9.0
            )),
            new EnrollmentDto(candidates[2], studentships[3], false, Map.of(
                8L, 5.0,
                9L, 2.0
            )),
            new EnrollmentDto(candidates[3], studentships[3], true, Map.of(
                8L, 7.0,
                9L, 8.0
            )),
            new EnrollmentDto(candidates[4], studentships[3], false, Map.of(
                8L, 0.0,
                9L, 6.0
            )),
        };
        for (EnrollmentDto enrollment : enrollments) {
            enrollmentService.createEnrollment(enrollment);
        }
    }
    
}
