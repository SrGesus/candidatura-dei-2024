package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Grade;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.GradeId;

@Repository
public interface GradeRepository extends JpaRepository<Grade, GradeId> {
}
