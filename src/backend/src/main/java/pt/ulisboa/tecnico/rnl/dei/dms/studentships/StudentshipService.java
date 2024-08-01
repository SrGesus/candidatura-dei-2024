package pt.ulisboa.tecnico.rnl.dei.dms.studentships;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.repository.StudentshipRepository;


@Service
public class StudentshipService {

    @Autowired
    private StudentshipRepository studentshipRepository;
    
    public StudentshipDto createStudentship(StudentshipDto studentshipDto) {
        Studentship studentship = new Studentship(studentshipDto);
        studentshipRepository.save(studentship);
        return new StudentshipDto(studentship);
    }

    public List<StudentshipDto> getStudentships() {
        return studentshipRepository.findAll().stream().map(StudentshipDto::new).collect(Collectors.toList());
    }

    public Optional<StudentshipDto> getStudentship(Long id) {
        return studentshipRepository.findById(id).map(StudentshipDto::new);
    }

    public List<StudentshipDto> updateStudentship(StudentshipDto studentshipDto) {
        Studentship studentship = studentshipRepository.findById(studentshipDto.getId()).get();
        studentship.setStartDate(studentshipDto.getStartDate());
        studentship.setEndDate(studentshipDto.getEndDate());
        studentship.setPay(studentshipDto.getPay());
        studentship.setVacancies(studentshipDto.getVacancies());
        studentshipRepository.save(studentship);
        // TODO: Not used Consider returning void
        return getStudentships();
    }

    public List<StudentshipDto> deleteStudentship(Long id) {
        studentshipRepository.deleteById(id);
        // TODO: Not used Consider returning void
        return getStudentships();
    }

    
}
