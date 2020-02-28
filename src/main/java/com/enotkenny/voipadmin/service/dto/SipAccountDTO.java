package com.enotkenny.voipadmin.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.enotkenny.voipadmin.domain.SipAccount} entity.
 */
public class SipAccountDTO implements Serializable {

    private Long id;

    private String username;

    private String password;

    private Boolean lineEnabled;

    private Integer lineNumber;

    private Boolean isManuallyCreated;


    private Long pbxAccountId;

    private Long deviceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isLineEnabled() {
        return lineEnabled;
    }

    public void setLineEnabled(Boolean lineEnabled) {
        this.lineEnabled = lineEnabled;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Boolean isIsManuallyCreated() {
        return isManuallyCreated;
    }

    public void setIsManuallyCreated(Boolean isManuallyCreated) {
        this.isManuallyCreated = isManuallyCreated;
    }

    public Long getPbxAccountId() {
        return pbxAccountId;
    }

    public void setPbxAccountId(Long pbxAccountId) {
        this.pbxAccountId = pbxAccountId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SipAccountDTO sipAccountDTO = (SipAccountDTO) o;
        if (sipAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sipAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SipAccountDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", lineEnabled='" + isLineEnabled() + "'" +
            ", lineNumber=" + getLineNumber() +
            ", isManuallyCreated='" + isIsManuallyCreated() + "'" +
            ", pbxAccount=" + getPbxAccountId() +
            ", device=" + getDeviceId() +
            "}";
    }
}
