package Reaktor_redes_main.repository;

import Reaktor_redes_main.models.Red;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedRepository extends JpaRepository<Red, Long> {
}
