package pt.ulisboa.tecnico.rnl.dei.dms.enrollments.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.Enrollment;
import pt.ulisboa.tecnico.rnl.dei.dms.enrollments.domain.EnrollmentId;

@Repository
@Transactional
public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
    List<Enrollment> findByCandidateIstId(Long candidateId);
    List<Enrollment> findByStudentshipId(Long studentshipId);
}
