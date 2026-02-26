package Reaktor_redes_main.repository;

import Reaktor_redes_main.models.HistorialEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialRepository extends JpaRepository<HistorialEstado, Long> {
    List<HistorialEstado> findBySsid(String ssid);
    HistorialEstado findTop1BySsidOrderByFechaReporteDesc(String ssid);
}
