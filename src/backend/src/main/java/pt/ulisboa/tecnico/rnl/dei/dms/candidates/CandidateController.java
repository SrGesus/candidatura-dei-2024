package pt.ulisboa.tecnico.rnl.dei.dms.candidates;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
    
    @Autowired
    private CandidateService candidateService;

    @PostMapping("/create")
    public CandidateDto createCandidate(@RequestBody CandidateDto candidateDto) {
        return candidateService.createCandidate(candidateDto);
    }

    @GetMapping("/all")
    public List<CandidateDto> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @PutMapping("/update")
    public CandidateDto updateCandidate(@RequestBody CandidateDto candidateDto) {
        return candidateService.updateCandidate(candidateDto);
    }

    @DeleteMapping("/delete/{istId}")
    public void deleteCandidate(@PathVariable Long istId) {
        candidateService.deleteCandidate(istId);
    }

    // Batch operations for convenience

    @PostMapping("/batch")
    public void createCandidates(@RequestBody List<CandidateDto> candidateDtos) {
        for (CandidateDto candidateDto : candidateDtos) {
            candidateService.createCandidate(candidateDto);
        }
    }

    @DeleteMapping("/batch")
    public void deleteCandidates(@RequestBody List<Long> istIds) {
        for (Long istId : istIds) {
            candidateService.deleteCandidate(istId);
        }
    }
}
