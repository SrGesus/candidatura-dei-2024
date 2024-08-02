package pt.ulisboa.tecnico.rnl.dei.dms.enrollments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.EnrollmentId;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.EnrollmentDto;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping(value = "/all",
                produces = "application/json; charset=utf-8")
    public List<EnrollmentDto> getEnrollments() {
        return enrollmentService.getEnrollments();
    }

    @GetMapping(value = "/candidate/{candidateId}",
                produces = "application/json; charset=utf-8")
    public List<EnrollmentDto> getCandidateEnrollments(@PathVariable Long candidateId) {
        return enrollmentService.getCandidateEnrollments(candidateId);
    }

    @GetMapping(value = "/studentship/{studentshipId}",
                produces = "application/json; charset=utf-8")
    public List<EnrollmentDto> getStudentshipEnrollments(@PathVariable Long studentshipId) {
        return enrollmentService.getStudentshipEnrollments(studentshipId);
    }

    @PostMapping(value = "/create")
    public EnrollmentDto createEnrollment(@RequestBody EnrollmentId enrollmentId) {
        return enrollmentService.createEnrollment(enrollmentId);
    }

    @DeleteMapping(value = "/delete")
    public List<EnrollmentDto> deleteEnrollment(@RequestBody EnrollmentId enrollmentId) {
        return enrollmentService.deleteEnrollment(enrollmentId);
    }
}
