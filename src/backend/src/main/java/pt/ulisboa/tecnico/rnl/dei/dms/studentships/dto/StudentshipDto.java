package pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;

public class StudentshipDto {
    
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double amount;
    private Integer vacancies;
    private Boolean active;
    private List<GradeParameterDto> gradeParameters;

    public StudentshipDto() {
    }

    public StudentshipDto(LocalDate startDate, LocalDate endDate, Double amount, Integer vacancies, Boolean active, List<GradeParameterDto> gradeParameters) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.vacancies = vacancies;
        this.active = active;
        this.gradeParameters = gradeParameters;
    }

    public StudentshipDto(Studentship studentship) {
        this.id = studentship.getId();
        this.startDate = studentship.getStartDate();
        this.endDate = studentship.getEndDate();
        this.amount = studentship.getAmount();
        this.vacancies = studentship.getVacancies();
        this.active = studentship.getActive();
        this.gradeParameters = studentship.getGradeParameters() != null ? studentship.getGradeParameters()
            .stream().map(GradeParameterDto::new).collect(Collectors.toList()) : null;
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

    public List<GradeParameterDto> getGradeParameters() {
        return gradeParameters;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setGradeParameters(List<GradeParameterDto> gradeParameters) {
        this.gradeParameters = gradeParameters;
    }
}
