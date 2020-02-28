package com.enotkenny.voipadmin.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.enotkenny.voipadmin.domain.PbxAccount} entity.
 */
public class PbxAccountDTO implements Serializable {

    private Long id;

    private String username;

    private String pbxId;


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

    public String getPbxId() {
        return pbxId;
    }

    public void setPbxId(String pbxId) {
        this.pbxId = pbxId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PbxAccountDTO pbxAccountDTO = (PbxAccountDTO) o;
        if (pbxAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pbxAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PbxAccountDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", pbxId='" + getPbxId() + "'" +
            "}";
    }
}
