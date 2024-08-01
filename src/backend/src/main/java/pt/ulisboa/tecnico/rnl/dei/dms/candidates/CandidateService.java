package pt.ulisboa.tecnico.rnl.dei.dms.candidates;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.repository.CandidateRepository;


@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    
    public CandidateDto createCandidate(CandidateDto candidateDto) {
        if (!candidateRepository.findById(candidateDto.getIstId()).isEmpty())
            throw new IllegalArgumentException("Candidate with istId " + candidateDto.getIstId() + " already exists");
        Candidate candidate = new Candidate(candidateDto);
        candidateRepository.save(candidate);
        return new CandidateDto(candidate);
    }

    public List<CandidateDto> getCandidates() {
        return candidateRepository.findAll().stream().map(CandidateDto::new).collect(Collectors.toList());
    }

    public Optional<CandidateDto> getCandidate(Long istId) {
        return candidateRepository.findById(istId).map(CandidateDto::new);
    }

    public List<CandidateDto> updateCandidate(CandidateDto candidateDto) {
        Candidate candidate = candidateRepository.findById(candidateDto.getIstId()).get();
        candidate.setIstId(candidateDto.getIstId());
        candidate.setName(candidateDto.getName());
        candidate.setEmail(candidateDto.getEmail());
        candidateRepository.save(candidate);
        // TODO: Not used Consider returning void
        return getCandidates();
    }

    public List<CandidateDto> deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
        // TODO: Not used Consider returning void
        return getCandidates();
    }

    
}
