package pt.ulisboa.tecnico.rnl.dei.dms.enrollments;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.EnrollmentDto;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/create")
    public EnrollmentDto createEnrollment(@RequestBody EnrollmentDto enrollmentDto) {
        return enrollmentService.createEnrollment(enrollmentDto);
    }

    @GetMapping("/all")
    public List<EnrollmentDto> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @GetMapping("/candidate/{istId}")
    public List<EnrollmentDto> getEnrollmentsByCandidateIstId(@PathVariable Long istId) {
        return enrollmentService.getEnrollmentsByCandidateIstId(istId);
    }

    @GetMapping("/studentship/{id}")
    public List<EnrollmentDto> getEnrollmentsByStudentshipId(@PathVariable Long id) {
        return enrollmentService.getEnrollmentsByStudentshipId(id);
    }

    @PutMapping("/update")
    public EnrollmentDto updateEnrollment(@RequestBody EnrollmentDto enrollmentDto) {
        return enrollmentService.updateEnrollment(enrollmentDto);
    }

    @DeleteMapping("/delete/{candidateIstId}/{studentshipId}")
    public void deleteEnrollment(@PathVariable Long candidateIstId, @PathVariable Long studentshipId) {
        enrollmentService.deleteEnrollment(candidateIstId, studentshipId);
    }

    // Batch operations for convenience

    @Transactional
    @PostMapping("/create/batch")
    public List<EnrollmentDto> createEnrollments(@RequestBody List<EnrollmentDto> enrollmentDtos) {
        return enrollmentDtos.stream()
            .map(e -> enrollmentService.createEnrollment(e))
            .collect(Collectors.toList());
    }

    @Transactional
    @PutMapping("/update/batch")
    public List<EnrollmentDto> updateEnrollments(@RequestBody List<EnrollmentDto> enrollmentDtos) {
        return enrollmentDtos.stream()
            .map(e -> enrollmentService.updateEnrollment(e))
            .collect(Collectors.toList());
    }

    @Transactional
    @DeleteMapping("/delete/batch")
    public void deleteEnrollments(@RequestBody List<EnrollmentDto> enrollmentDtos) {
        enrollmentDtos.forEach(e -> enrollmentService.deleteEnrollment(e.getCandidate().getIstId(), e.getStudentship().getId()));
    }

    
}
