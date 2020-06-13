package inter.venture.project.domain.user.repository;

import inter.venture.project.domain.user.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findByName(String name);

    Device findByDescription(String description);

    @Query("SELECT d FROM Device d where d.creator.username = ?1")
    List<Device> listUserDevices(String username);
}
