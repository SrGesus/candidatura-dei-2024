package pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;

@Entity
@Table(name = "studentship")
public class Studentship {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Integer vacancies;

    @Column(nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "studentship", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GradeParameter> gradeParameters;

    public Studentship() {
    }

    public Studentship(StudentshipDto studentshipDto) {
        this.id = studentshipDto.getId();
        this.startDate = studentshipDto.getStartDate();
        this.endDate = studentshipDto.getEndDate();
        this.amount = studentshipDto.getAmount();
        this.vacancies = studentshipDto.getVacancies();
        this.active = studentshipDto.getActive();
        this.gradeParameters = studentshipDto.getGradeParameters() != null ? studentshipDto.getGradeParameters()
            .stream().map(gradeParameterDto -> new GradeParameter(gradeParameterDto, this)).collect(Collectors.toList()) : null;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getVacancies() {
        return vacancies;
    }

    public Boolean getActive() {
        return active;
    }

    public List<GradeParameter> getGradeParameters() {
        return gradeParameters;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setVacancies(Integer vacancies) {
        this.vacancies = vacancies;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setGradeParameters(List<GradeParameter> gradeParameters) {
        this.gradeParameters = gradeParameters;
    }
}
