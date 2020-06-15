package inter.venture.project.domain.user.repository;

import inter.venture.project.domain.user.entity.Device;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findByName(String name);

    Device findByDescription(String description);

    @Query("SELECT d FROM Device d where d.creator.username = ?1")
    List<Device> listUserDevices(String username);

    @Query("UPDATE Device d SET d.name = ?1, d.description = ?2, d.secret = ?3, d.properties = ?4")
    Device updateDevice(String name, String description, String secret, String properties);

    Device findByProperties(String property);

}
