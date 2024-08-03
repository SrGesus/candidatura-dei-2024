package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.GradeParameter;

@Repository
public interface GradeParameterRepository extends JpaRepository<GradeParameter, Long> {
    List<GradeParameter> findByStudentshipIdOrderById(Long studentshipId);
}
