package pt.ulisboa.tecnico.rnl.dei.dms.studentships;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/studentships")
public class StudentshipController {
    
    @Autowired
    private StudentshipService studentshipService;

    @GetMapping(value = "/all",
                produces = "application/json; charset=utf-8")
    public List<StudentshipDto>
    getStudentships() {
      return studentshipService.getStudentships();
    }

    @GetMapping(value = "/get/{id}",
                produces = "application/json; charset=utf-8")
    public StudentshipDto getStudentship(@PathVariable Long id) {
        return studentshipService.getStudentship(id).orElseThrow(
            NotFoundException::new
        );
    }

    @PostMapping("/create")
    public StudentshipDto createStudentship(@RequestBody StudentshipDto studentshipDto) {
        return studentshipService.createStudentship(studentshipDto);
    }

    @PutMapping("/update")
    public List<StudentshipDto> updateStudentship(@RequestBody StudentshipDto studentshipDto) {
        return studentshipService.updateStudentship(studentshipDto);
    }

    @DeleteMapping("/delete/{id}")
    public List<StudentshipDto> deleteStudentship(@PathVariable Long id) {
        return studentshipService.deleteStudentship(id);
    }

}
