package pt.ulisboa.tecnico.rnl.dei.dms.enrollments;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.EnrollmentId;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.repository.CandidateRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.EntitityAlreadyExists;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.NotFoundException;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.GradeParameter;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.repository.GradeParameterRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.repository.StudentshipRepository;

@Service
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private StudentshipRepository studentshipRepository;

    @Autowired
    private GradeParameterRepository gradeParameterRepository;

    @Transactional
    public EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto) {
        Candidate candidate = candidateRepository.findById(enrollmentDto.getCandidate().getIstId()).orElseThrow(
            () -> new NotFoundException("Candidate with IST ID " + enrollmentDto.getCandidate().getIstId() + " not found")
        );
        Studentship studentship = studentshipRepository.findById(enrollmentDto.getStudentship().getId()).orElseThrow(
            () -> new NotFoundException("Studentship with ID " + enrollmentDto.getStudentship().getId() + " not found")
        );
        if (enrollmentRepository.findById(new EnrollmentId(enrollmentDto.getCandidate().getIstId(), enrollmentDto.getStudentship().getId())).isPresent()) {
            throw new EntitityAlreadyExists("Enrollment with Candidate IST ID " + enrollmentDto.getCandidate().getIstId() + " and Studentship ID " + enrollmentDto.getStudentship().getId() + " already exists");
        }
        Map<GradeParameter, Double> grades = enrollmentDto.getGrades() != null ? this.gradeFromIds(enrollmentDto.getGrades()) : Map.of();   
        // TODO: Consider checking if grade parameters are from the same studentship
        // Perhaps desnecessary since nothing of importance happens if they are not
        Enrollment enrollment = new Enrollment(candidate, studentship, enrollmentDto.getAccepted(), grades);
        enrollment = enrollmentRepository.save(enrollment);
        return new EnrollmentDto(enrollment);
    }

    public List<EnrollmentDto> getAllEnrollments() {
        return enrollmentRepository.findAll().stream().map(EnrollmentDto::new).collect(Collectors.toList());
    }

    public List<EnrollmentDto> getEnrollmentsByCandidateIstId(Long candidateIstId) {
        return enrollmentRepository.findByCandidateIstIdOrderByStudentshipId(candidateIstId).stream().map(EnrollmentDto::new).collect(Collectors.toList());
    }

    public List<EnrollmentDto> getEnrollmentsByStudentshipId(Long studentshipId) {
        return enrollmentRepository.findByStudentshipIdOrderByCandidateIstId(studentshipId).stream().map(EnrollmentDto::new).collect(Collectors.toList());
    }

    @Transactional
    public EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto) {
        Enrollment enrollment = enrollmentRepository.findById(new EnrollmentId(enrollmentDto.getCandidate().getIstId(), enrollmentDto.getStudentship().getId())).orElseThrow(
            () -> new NotFoundException("Enrollment with Candidate IST ID " + enrollmentDto.getCandidate().getIstId() + " and Studentship ID " + enrollmentDto.getStudentship().getId() + " not found")
        );
        enrollment.setAccepted(enrollmentDto.getAccepted());
        enrollment.setGrades(enrollmentDto.getGrades() != null ? this.gradeFromIds(enrollmentDto.getGrades()) : enrollment.getGrades());
        enrollment = enrollmentRepository.save(enrollment);
        return new EnrollmentDto(enrollment);
    }

    public void deleteEnrollment(Long candidateIstId, Long studentshipId) {
        Enrollment enrollment = enrollmentRepository.findById(new EnrollmentId(candidateIstId, studentshipId)).orElseThrow(
            () -> new NotFoundException("Enrollment with Candidate IST ID " + candidateIstId + " and Studentship ID " + studentshipId + " not found")
        );
        enrollmentRepository.delete(enrollment);
    }

    private Map<GradeParameter, Double> gradeFromIds(Map<Long, Double> grades) throws NotFoundException {
        return grades.entrySet().stream().collect(
            Collectors.toMap(
                // TODO: Verify is grade is not greater than the grade parameter weight
                e -> gradeParameterRepository.findById(e.getKey()).orElseThrow(
                    () -> new NotFoundException("Grade Parameter with ID " + e.getKey() + " not found")
                ),
                Map.Entry::getValue
            )
        );
    }
}
