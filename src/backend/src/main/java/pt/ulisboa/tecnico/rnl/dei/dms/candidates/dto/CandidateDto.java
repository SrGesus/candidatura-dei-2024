package pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain.Candidate;

public class CandidateDto {
    
    private Long istId;
    private String name;
    private String email;

    public CandidateDto() {
    }

    public CandidateDto(Long istId, String name, String email) {
        this.istId = istId;
        this.name = name;
        this.email = email;
    }

    public CandidateDto(Candidate candidate) {
        this.istId = candidate.getIstId();
        this.name = candidate.getName();
        this.email = candidate.getEmail();
    }

    public Long getIstId() {
        return istId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setIstId(Long istId) {
        this.istId = istId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
