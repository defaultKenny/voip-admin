package com.enotkenny.voipadmin.repository;
import com.enotkenny.voipadmin.domain.DeviceSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeviceSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceSettingRepository extends JpaRepository<DeviceSetting, Long>, JpaSpecificationExecutor<DeviceSetting> {

}
