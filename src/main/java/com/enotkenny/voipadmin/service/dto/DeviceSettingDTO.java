package com.enotkenny.voipadmin.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.enotkenny.voipadmin.domain.DeviceSetting} entity.
 */
public class DeviceSettingDTO implements Serializable {

    private Long id;

    private String value;


    private Long deviceId;

    private String deviceMac;

    private Long additionalOptionId;

    private String additionalOptionCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public Long getAdditionalOptionId() {
        return additionalOptionId;
    }

    public void setAdditionalOptionId(Long additionalOptionId) {
        this.additionalOptionId = additionalOptionId;
    }

    public String getAdditionalOptionCode() {
        return additionalOptionCode;
    }

    public void setAdditionalOptionCode(String additionalOptionCode) {
        this.additionalOptionCode = additionalOptionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeviceSettingDTO deviceSettingDTO = (DeviceSettingDTO) o;
        if (deviceSettingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceSettingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceSettingDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", device=" + getDeviceId() +
            ", device='" + getDeviceMac() + "'" +
            ", additionalOption=" + getAdditionalOptionId() +
            ", additionalOption='" + getAdditionalOptionCode() + "'" +
            "}";
    }
}
