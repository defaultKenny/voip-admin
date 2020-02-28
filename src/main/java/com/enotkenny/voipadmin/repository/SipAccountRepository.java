package com.enotkenny.voipadmin.repository;
import com.enotkenny.voipadmin.domain.SipAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SipAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SipAccountRepository extends JpaRepository<SipAccount, Long>, JpaSpecificationExecutor<SipAccount> {

}
