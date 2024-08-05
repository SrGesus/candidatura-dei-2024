package pt.ulisboa.tecnico.rnl.dei.dms.candidates;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.repository.CandidateRepository;
import pt.ulisboa.tecnico.rnl.dei.dms.exceptions.NotFoundException;


@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateDto createCandidate(CandidateDto candidateDto) {
        Candidate candidate = new Candidate(candidateDto);
        candidateRepository.save(candidate);
        return new CandidateDto(candidate);
    }

    public List<CandidateDto> getAllCandidates() {
        return candidateRepository.findAll().stream().map(CandidateDto::new).collect(Collectors.toList());
    }

    public CandidateDto updateCandidate(CandidateDto candidateDto) {
        Candidate candidate = candidateRepository.findById(candidateDto.getIstId()).orElseThrow(
            () -> new NotFoundException("Candidate with IST ID " + candidateDto.getIstId() + " not found")
        );
        candidate.setName(candidateDto.getName());
        candidate.setEmail(candidateDto.getEmail());
        candidateRepository.save(candidate);
        return new CandidateDto(candidate);
    }

    public void deleteCandidate(Long istId) {
        Candidate candidate = candidateRepository.findById(istId).orElseThrow(
            () -> new NotFoundException("Candidate with IST ID " + istId + " not found")
        );
        candidateRepository.delete(candidate);  
    }
}
