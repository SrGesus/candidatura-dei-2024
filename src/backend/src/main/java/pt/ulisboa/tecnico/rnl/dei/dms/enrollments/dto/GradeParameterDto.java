package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.dto;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.GradeParameter;

public class GradeParameterDto {

    private Long id;
    private String name;
    private Float weight;
    private Long studentshipId;

    public GradeParameterDto() {
    }

    public GradeParameterDto(String name, Float weight, Long studentshipId) {
        this.name = name;
        this.weight = weight;
        this.studentshipId = studentshipId;
    }

    public GradeParameterDto(GradeParameter gradeParameter) {
        this.id = gradeParameter.getId();
        this.name = gradeParameter.getName();
        this.weight = gradeParameter.getWeight();
        this.studentshipId = gradeParameter.getStudentship().getId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getWeight() {
        return weight;
    }

    public Long getStudentshipId() {
        return studentshipId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setStudentshipId(Long studentshipId) {
        this.studentshipId = studentshipId;
    }
}