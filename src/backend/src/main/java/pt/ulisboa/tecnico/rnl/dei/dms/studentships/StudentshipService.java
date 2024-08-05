package pt.ulisboa.tecnico.rnl.dei.dms.studentships;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.EntitityAlreadyExists;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.NotFoundException;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.repository.StudentshipRepository;


@Service
public class StudentshipService {
    
    @Autowired
    private StudentshipRepository studentshipRepository;
    

    @Transactional
    public StudentshipDto createStudentship(StudentshipDto studentshipDto) {
        Studentship studentship = new Studentship(studentshipDto);
        studentship = studentshipRepository.save(studentship);
        return new StudentshipDto(studentship);
    }

    public List<StudentshipDto> getAllStudentships() {
        return studentshipRepository.findAll().stream().map(StudentshipDto::new).collect(Collectors.toList());
    }

    public StudentshipDto getStudentship(Long id) {
        Studentship studentship = studentshipRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Studentship with ID " + id + " not found")
        );
        return new StudentshipDto(studentship);
    }

    @Transactional
    public StudentshipDto updateStudentship(StudentshipDto studentshipDto) {
        Studentship studentship = studentshipRepository.findById(studentshipDto.getId()).orElseThrow(
            () -> new NotFoundException("Studentship with ID " + studentshipDto.getId() + " not found")
        );
        Studentship updatedStudentship = new Studentship(studentshipDto);
        // grade parameters are unchanged if not provided
        updatedStudentship.setGradeParameters(
            updatedStudentship.getGradeParameters() != null ? updatedStudentship.getGradeParameters()
                : studentship.getGradeParameters()
        );
        studentshipRepository.save(updatedStudentship);
        return new StudentshipDto(studentship);
    }   

    public void deleteStudentship(Long id) {
        Studentship studentship = studentshipRepository.findById(id).orElseThrow(
            () -> new NotFoundException("Studentship with ID " + id + " not found")
        );
        studentshipRepository.delete(studentship);
    }
}
