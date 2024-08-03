package pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto.StudentshipDto;

@Entity
@Table(name = "studentship")
public class Studentship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    private Float pay;

    private Integer vacancies;

    public Studentship() {

    }

    public Studentship(LocalDate startDate, LocalDate endDate, Float pay, Integer vacancies) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.pay = pay;
        this.vacancies = vacancies;
    }

    public Studentship(StudentshipDto studentshipDto) {
        this.startDate = studentshipDto.getStartDate();
        this.endDate = studentshipDto.getEndDate();
        this.pay = studentshipDto.getPay();
        this.vacancies = studentshipDto.getVacancies();
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
