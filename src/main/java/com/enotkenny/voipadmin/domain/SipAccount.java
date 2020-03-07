package com.enotkenny.voipadmin.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A SipAccount.
 */
@Entity
@Table(name = "sip_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SipAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "line_enabled")
    private Boolean lineEnabled;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "is_manually_created")
    private Boolean isManuallyCreated;

    @OneToOne(fetch = FetchType.LAZY)

    @JoinColumn(unique = true)
    private PbxAccount pbxAccount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @NotNull
//    @JsonIgnoreProperties("sipAccounts")
    private Device device;

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

    public SipAccount username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public SipAccount password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isLineEnabled() {
        return lineEnabled;
    }

    public SipAccount lineEnabled(Boolean lineEnabled) {
        this.lineEnabled = lineEnabled;
        return this;
    }

    public void setLineEnabled(Boolean lineEnabled) {
        this.lineEnabled = lineEnabled;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public SipAccount lineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Boolean isIsManuallyCreated() {
        return isManuallyCreated;
    }

    public SipAccount isManuallyCreated(Boolean isManuallyCreated) {
        this.isManuallyCreated = isManuallyCreated;
        return this;
    }

    public void setIsManuallyCreated(Boolean isManuallyCreated) {
        this.isManuallyCreated = isManuallyCreated;
    }

    public PbxAccount getPbxAccount() {
        return pbxAccount;
    }

    public SipAccount pbxAccount(PbxAccount pbxAccount) {
        this.pbxAccount = pbxAccount;
        return this;
    }

    public void setPbxAccount(PbxAccount pbxAccount) {
        this.pbxAccount = pbxAccount;
    }

    public Device getDevice() {
        return device;
    }

    public SipAccount device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SipAccount)) {
            return false;
        }
        return id != null && id.equals(((SipAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SipAccount{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", lineEnabled='" + isLineEnabled() + "'" +
            ", lineNumber=" + getLineNumber() +
            ", isManuallyCreated='" + isIsManuallyCreated() + "'" +
            "}";
    }
}
