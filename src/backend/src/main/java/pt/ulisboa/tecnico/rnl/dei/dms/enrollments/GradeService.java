package pt.ulisboa.tecnico.rnl.dei.dms.enrollments;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.EnrollmentId;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Grade;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.GradeParameter;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.EnrollmentDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.GradeDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.GradeParameterDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.EnrollmentRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.GradeParameterRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository.GradeRepository;
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

    @Autowired
    private GradeRepository gradeRepository;

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

    public GradeDto gradeCandidate(GradeDto gradeDto) {
        EnrollmentId enrollmentId = new EnrollmentId(gradeDto.getCandidateIstId(), gradeDto.getStudentshipId());
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(
            () ->
            new NotFoundException("Enrollment with id " + enrollmentId + " not found")
        );
        GradeParameter gradeParameter = gradeParameterRepository.findById(gradeDto.getGradeParameterId()).orElseThrow(
            () ->
            new NotFoundException("Grade parameter with id " + gradeDto.getGradeParameterId() + " not found")
        );
        Grade grade = new Grade(gradeParameter, enrollment, gradeDto.getGrade());
        gradeRepository.save(grade);
        return new GradeDto(grade);
    }
}
