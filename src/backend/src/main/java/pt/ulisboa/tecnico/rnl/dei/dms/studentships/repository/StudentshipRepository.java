package pt.ulisboa.tecnico.rnl.dei.dms.studentships.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.dms.studentships.domain.Studentship;

@Repository
@Transactional
public interface StudentshipRepository extends JpaRepository<Studentship, Long> {
    public List<Studentship> findAllByOrderById();
}
