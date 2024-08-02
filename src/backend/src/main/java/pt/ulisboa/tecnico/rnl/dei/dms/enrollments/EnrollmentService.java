package pt.ulisboa.tecnico.rnl.dei.dms.enrollments;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.repository.CandidateRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.repository.StudentshipRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.EnrollmentId;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.NotFoundException;


@Service
public class EnrollmentService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private StudentshipRepository studentshipRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<EnrollmentDto> getEnrollments() {
        return enrollmentRepository.findAll().stream()
            .map(EnrollmentDto::new).collect(Collectors.toList());
    }
    
    public List<EnrollmentDto> getCandidateEnrollments(Long candidateId) {
        return enrollmentRepository.findByCandidateIstId(candidateId).stream()
            .map(EnrollmentDto::new).collect(Collectors.toList());
    }

    public List<EnrollmentDto> getStudentshipEnrollments(Long studentshipId) {
        return enrollmentRepository.findByStudentshipId(studentshipId).stream()
            .map(EnrollmentDto::new).collect(Collectors.toList());
    }

    public EnrollmentDto createEnrollment(EnrollmentId enrollmentId) {
        Candidate candidate = candidateRepository.findById(enrollmentId.getCandidateId()).orElseThrow(
            NotFoundException::new
        );
        Studentship studentship = studentshipRepository.findById(enrollmentId.getStudentshipId()).orElseThrow(
            NotFoundException::new
        );
        Enrollment enrollment = new Enrollment(candidate, studentship);
        enrollmentRepository.save(enrollment);
        return new EnrollmentDto(enrollment);
    }

    public List<EnrollmentDto> deleteEnrollment(EnrollmentId enrollmentId) {
        enrollmentRepository.deleteById(enrollmentId);
        return getEnrollments();
    }
}
