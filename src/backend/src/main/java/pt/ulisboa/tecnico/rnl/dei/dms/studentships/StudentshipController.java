package pt.ulisboa.tecnico.rnl.dei.dms.studentships;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;

@RestController
@RequestMapping("/studentships")
public class StudentshipController {
    
    @Autowired
    private StudentshipService studentshipService;

    @PostMapping("/create")
    public StudentshipDto createStudentship(@RequestBody StudentshipDto studentshipDto) {
        return studentshipService.createStudentship(studentshipDto);
    }

    @GetMapping("/all")
    public List<StudentshipDto> getAllStudentships() {
        return studentshipService.getAllStudentships();
    }

    @GetMapping("/{id}")
    public StudentshipDto getStudentship(@PathVariable Long id) {
        return studentshipService.getStudentship(id);
    }

    @PutMapping("/update")
    public StudentshipDto updateStudentship(@RequestBody StudentshipDto studentshipDto) {
        return studentshipService.updateStudentship(studentshipDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudentship(@PathVariable Long id) {
        studentshipService.deleteStudentship(id);
    }
}
