package pt.ulisboa.tecnico.rnl.dei.dms.enrollments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.EnrollmentId;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.GradeDto;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto.GradeParameterDto;

@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping(value = "/studentship/{studentshipId}",
                produces = "application/json; charset=utf-8")
    public List<GradeParameterDto> geGradeParameters(@PathVariable Long studentshipId) {
        return gradeService.getStudentshipParameters(studentshipId);
    }

    @PostMapping(value = "/create")
    public GradeParameterDto createGradeParameter(@RequestBody GradeParameterDto gradeParameterDto) {
        return gradeService.createGradeParameter(gradeParameterDto);
    }

    @PutMapping(value = "/update")
    public GradeParameterDto updateGradeParameter(@RequestBody GradeParameterDto gradeParameterDto) {
        return gradeService.updateGradeParameter(gradeParameterDto);
    }

    @DeleteMapping(value = "/delete/{gradeParameterId}")
    public void deleteGradeParameter(@PathVariable Long gradeParameterId) {
        gradeService.deleteGradeParameter(gradeParameterId);
    }

    @PostMapping(value = "/grade")
    public GradeDto gradeCandidate(@RequestBody GradeDto gradeDto) {
        return gradeService.gradeCandidate(gradeDto);
    }


}
