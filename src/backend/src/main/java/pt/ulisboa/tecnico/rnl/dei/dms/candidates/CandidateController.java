package pt.ulisboa.tecnico.rnl.dei.dms.candidates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
    
    @Autowired
    private CandidateService candidateService;

    @GetMapping(value = "/all",
                produces = "application/json; charset=utf-8")
    public List<CandidateDto>
    getCandidates() {
      return candidateService.getCandidates();
    }

    @GetMapping(value = "/get/{id}",
                produces = "application/json; charset=utf-8")
    public CandidateDto getCandidate(@PathVariable Long istId) {
        return candidateService.getCandidate(istId).orElseThrow(
            () -> new NotFoundException("Candidate with istId " + istId + " not found")
        );
    }

    @PostMapping("/create")
    public CandidateDto createCandidate(@RequestBody CandidateDto candidateDto) {
        return candidateService.createCandidate(candidateDto);
    }

    @PutMapping("/update")
    public List<CandidateDto> updateCandidate(@RequestBody CandidateDto candidateDto) {
        return candidateService.updateCandidate(candidateDto);
    }

    @DeleteMapping("/delete/{istId}")
    public List<CandidateDto> deleteCandidate(@PathVariable Long istId) {
        return candidateService.deleteCandidate(istId);
    }

}
