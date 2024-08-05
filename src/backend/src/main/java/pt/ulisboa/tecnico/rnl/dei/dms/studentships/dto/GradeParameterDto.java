package pt.ulisboa.tecnico.rnl.dei.dms.studentships.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.GradeParameter;

public class GradeParameterDto {
    
    private Long id;
    private String name;
    private Double weight;

    public GradeParameterDto() {
    }

    public GradeParameterDto(String name, Double weight) {
        this.id = null;
        this.name = name;
        this.weight = weight;
    }

    public GradeParameterDto(GradeParameter gradeParameter) {
        this.id = gradeParameter.getId();
        this.name = gradeParameter.getName();
        this.weight = gradeParameter.getWeight();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
