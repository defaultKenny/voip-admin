package com.enotkenny.voipadmin.repository;
import com.enotkenny.voipadmin.domain.PbxAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PbxAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PbxAccountRepository extends JpaRepository<PbxAccount, Long>, JpaSpecificationExecutor<PbxAccount> {

}
