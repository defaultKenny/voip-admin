package com.enotkenny.voipadmin.repository;
import com.enotkenny.voipadmin.domain.DeviceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DeviceModel entity.
 */
@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long>, JpaSpecificationExecutor<DeviceModel> {

    @Query(value = "select distinct deviceModel from DeviceModel deviceModel left join fetch deviceModel.additionalOptions",
        countQuery = "select count(distinct deviceModel) from DeviceModel deviceModel")
    Page<DeviceModel> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct deviceModel from DeviceModel deviceModel left join fetch deviceModel.additionalOptions")
    List<DeviceModel> findAllWithEagerRelationships();

    @Query("select deviceModel from DeviceModel deviceModel left join fetch deviceModel.additionalOptions where deviceModel.id =:id")
    Optional<DeviceModel> findOneWithEagerRelationships(@Param("id") Long id);

}
