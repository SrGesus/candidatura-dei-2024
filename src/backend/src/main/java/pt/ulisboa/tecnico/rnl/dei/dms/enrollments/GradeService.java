package pt.ulisboa.tecnico.rnl.dei.dms.enrollments;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.EnrollmentId;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.GradeParameter;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.GradeParameterDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.GradeParameterRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.NotFoundException;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.repository.StudentshipRepository;

@Service
public class GradeService {

    @Autowired
    private GradeParameterRepository gradeParameterRepository;

    @Autowired
    private StudentshipRepository studentshipRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<GradeParameterDto> getStudentshipParameters(Long studentshipId) {
        return gradeParameterRepository.findByStudentshipIdOrderById(studentshipId).stream()
            .map(GradeParameterDto::new).collect(Collectors.toList());
    }

    public GradeParameterDto createGradeParameter(GradeParameterDto gradeParameterDto) {
        Studentship studentship = studentshipRepository.findById(gradeParameterDto.getStudentshipId()).orElseThrow(
            () ->
            new NotFoundException("Studentship with id " + gradeParameterDto.getStudentshipId() + " not found")
        );
        GradeParameter gradeParameter = new GradeParameter(gradeParameterDto.getName(), gradeParameterDto.getWeight(), studentship);
        gradeParameterRepository.save(gradeParameter);
        return new GradeParameterDto(gradeParameter);
    }

    public GradeParameterDto updateGradeParameter(GradeParameterDto gradeParameterDto) {
        GradeParameter gradeParameter = gradeParameterRepository.findById(gradeParameterDto.getId()).orElseThrow(
            () ->
            new NotFoundException("Grade parameter with id " + gradeParameterDto.getId() + " not found")
        );
        gradeParameter.setName(gradeParameterDto.getName());
        gradeParameter.setWeight(gradeParameterDto.getWeight());
        gradeParameterRepository.save(gradeParameter);
        return new GradeParameterDto(gradeParameter);
    }

    public void deleteGradeParameter(Long gradeParameterId) {
        GradeParameter gradeParameter = gradeParameterRepository.findById(gradeParameterId).orElseThrow(
            () ->
            new NotFoundException("Grade parameter with id " + gradeParameterId + " not found")
        );
        gradeParameterRepository.delete(gradeParameter);
    }

    public Enrollment gradeCandidate(EnrollmentId enrollmentId, GradeParameterDto gradeParameterDto, Double grade) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(
            () ->
            new NotFoundException("Enrollment with id " + enrollmentId + " not found")
        );
        GradeParameter gradeParameter = gradeParameterRepository.findById(gradeParameterDto.getId()).orElseThrow(
            () ->
            new NotFoundException("Grade parameter with id " + gradeParameterDto.getId() + " not found")
        );
        enrollment.getGrades().put(gradeParameter, grade);
        enrollmentRepository.save(enrollment);
        return enrollment;
    }

}
