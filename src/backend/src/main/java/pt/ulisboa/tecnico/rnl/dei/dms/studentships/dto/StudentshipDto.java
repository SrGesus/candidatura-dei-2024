package pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto;

import java.time.LocalDate;
import java.util.Date;

import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;

public class StudentshipDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Float pay;
    private Integer vacancies;

    public StudentshipDto() {
    }

    public StudentshipDto(LocalDate startDate, LocalDate endDate, Float pay, Integer vacancies) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.pay = pay;
        this.vacancies = vacancies;
    }

    public StudentshipDto(Studentship studentship) {
        this.id = studentship.getId();
        this.startDate = studentship.getStartDate();
        this.endDate = studentship.getEndDate();
        this.pay = studentship.getPay();
        this.vacancies = studentship.getVacancies();
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

    public Float getPay() {
        return pay;
    }

    public Integer getVacancies() {
        return vacancies;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setPay(Float pay) {
        this.pay = pay;
    }

    public void setVacancies(Integer vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public String toString() {
        return "Studentship{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pay=" + pay +
                ", vacancies=" + vacancies +
                '}';
    }

}