package com.enotkenny.voipadmin.repository;
import com.enotkenny.voipadmin.domain.AdditionalOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AdditionalOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdditionalOptionRepository extends JpaRepository<AdditionalOption, Long>, JpaSpecificationExecutor<AdditionalOption> {

}
