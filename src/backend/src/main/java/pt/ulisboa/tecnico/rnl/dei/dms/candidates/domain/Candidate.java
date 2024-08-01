package pt.ulisboa.tecnico.rnl.dei.dms.candidates.domain;



import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.candidates.dto.CandidateDto;

@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    private Long istId;

    private String name;

    private String email;

    public Candidate() {

    }

    public Candidate(Long istId, String name, String email) {
        this.istId = istId;
        this.name = name;
        this.email = email;
    }

    public Candidate(CandidateDto candidateDto) {
        this.istId = candidateDto.getIstId();
        this.name = candidateDto.getName();
        this.email = candidateDto.getEmail();
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

    @Override
    public String toString() {
        return "Candidate{" +
                "istId=" + istId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
