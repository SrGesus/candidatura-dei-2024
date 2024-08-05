package pt.ulisboa.tecnico.rnl.dei.dms.enrollments;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.EnrollmentId;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.repository.CandidateRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.NotFoundException;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.repository.StudentshipRepository;

@Service
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private StudentshipRepository studentshipRepository;

    public EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto) {
        Candidate candidate = candidateRepository.findById(enrollmentDto.getCandidateIstId()).orElseThrow(
            () -> new NotFoundException("Candidate with IST ID " + enrollmentDto.getCandidateIstId() + " not found")
        );
        Studentship studentship = studentshipRepository.findById(enrollmentDto.getStudentshipId()).orElseThrow(
            () -> new NotFoundException("Studentship with ID " + enrollmentDto.getStudentshipId() + " not found")
        );
        Enrollment enrollment = new Enrollment(candidate, studentship, enrollmentDto.getAccepted());
        enrollment = enrollmentRepository.save(enrollment);
        return new EnrollmentDto(enrollment);
    }

    public List<EnrollmentDto> getAllEnrollments() {
        return enrollmentRepository.findAll().stream().map(EnrollmentDto::new).collect(Collectors.toList());
    }

    public List<EnrollmentDto> getEnrollmentsByCandidateIstId(Long candidateIstId) {
        return enrollmentRepository.findByCandidateIstId(candidateIstId).stream().map(EnrollmentDto::new).collect(Collectors.toList());
    }

    public List<EnrollmentDto> getEnrollmentsByStudentshipId(Long studentshipId) {
        return enrollmentRepository.findByStudentshipId(studentshipId).stream().map(EnrollmentDto::new).collect(Collectors.toList());
    }

    public EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto) {
        Enrollment enrollment = enrollmentRepository.findById(new EnrollmentId(enrollmentDto.getCandidateIstId(), enrollmentDto.getStudentshipId())).orElseThrow(
            () -> new NotFoundException("Enrollment with Candidate IST ID " + enrollmentDto.getCandidateIstId() + " and Studentship ID " + enrollmentDto.getStudentshipId() + " not found")
        );
        enrollment.setAccepted(enrollmentDto.getAccepted());
        enrollment = enrollmentRepository.save(enrollment);
        return new EnrollmentDto(enrollment);
    }

    public void deleteEnrollment(Long candidateIstId, Long studentshipId) {
        Enrollment enrollment = enrollmentRepository.findById(new EnrollmentId(candidateIstId, studentshipId)).orElseThrow(
            () -> new NotFoundException("Enrollment with Candidate IST ID " + candidateIstId + " and Studentship ID " + studentshipId + " not found")
        );
        enrollmentRepository.delete(enrollment);
    }
}
