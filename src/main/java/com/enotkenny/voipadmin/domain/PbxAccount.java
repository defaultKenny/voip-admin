package com.enotkenny.voipadmin.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PbxAccount.
 */
@Entity
@Table(name = "pbx_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PbxAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "pbx_id")
    private String pbxId;

    @OneToOne(mappedBy = "pbxAccount")
    @JsonIgnore
    private SipAccount sipAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public PbxAccount username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPbxId() {
        return pbxId;
    }

    public PbxAccount pbxId(String pbxId) {
        this.pbxId = pbxId;
        return this;
    }

    public void setPbxId(String pbxId) {
        this.pbxId = pbxId;
    }

    public SipAccount getSipAccount() {
        return sipAccount;
    }

    public PbxAccount sipAccount(SipAccount sipAccount) {
        this.sipAccount = sipAccount;
        return this;
    }

    public void setSipAccount(SipAccount sipAccount) {
        this.sipAccount = sipAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PbxAccount)) {
            return false;
        }
        return id != null && id.equals(((PbxAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PbxAccount{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", pbxId='" + getPbxId() + "'" +
            "}";
    }
}
