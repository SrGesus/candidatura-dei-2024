package pt.ulisboa.tecnico.rnl.dei.dms;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.CandidateService;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.EnrollmentService;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.GradeService;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.EnrollmentId;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.GradeDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.GradeParameterDto;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.StudentshipService;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;

@Component
public class DmsMockData implements CommandLineRunner {

    @Autowired
    CandidateService candidateService;

    @Autowired
    StudentshipService studentshipService;

    @Autowired
    EnrollmentService enrollmentService;

    @Autowired
    GradeService gradeService;

    @Override
    public void run(String... args) throws Exception {
        // Candidates
        CandidateDto[] candidates = {
            new CandidateDto(802L, "Joaquim Alberto", "joalberto43@portugal.gov"),
            new CandidateDto(803L, "Maria Albertina", "mariaa@tecnico.ulisboa.pt"),
            new CandidateDto(809L, "Vanessa Albertina", "candidaturas@vanessa.pt"),
            new CandidateDto(810L, "Candidato 810", "email@mail.com"),
            new CandidateDto(811L, "Candidato 811", "email@mail.com"),
            new CandidateDto(812L, "Candidato 812", "email@mail.com"),
            new CandidateDto(813L, "Candidato 813", "email@mail.com"),
            new CandidateDto(814L, "Candidato 814", "email@mail.com"),
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
            new EnrollmentId(802L, 1L),
            new EnrollmentId(803L, 1L),
            new EnrollmentId(813L, 1L),
        };
        for (EnrollmentId enrollment : enrollments) {
            enrollmentService.createEnrollment(enrollment);
        }
        // Grade Parameters
        gradeService.createGradeParameter(new GradeParameterDto("Exercício Prático", 50.0f, 1L));
        gradeService.createGradeParameter(new GradeParameterDto("Entrevista", 30.0f, 1L));
        gradeService.createGradeParameter(new GradeParameterDto("Avaliação Curricular", 20.0f, 1L));
        
        // Grades
        gradeService.gradeCandidate(new GradeDto(1L, 803L, 1L, 40.0f));
        gradeService.gradeCandidate(new GradeDto(2L, 803L, 1L, 30.0f));
        gradeService.gradeCandidate(new GradeDto(3L, 803L, 1L, 5.0f));
    }
}
