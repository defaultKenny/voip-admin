package com.enotkenny.voipadmin.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.enotkenny.voipadmin.domain.DeviceModel} entity.
 */
public class DeviceModelDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Boolean isConfigurable;

    private Integer linesCount;

    @Lob
    private byte[] configFile;

    private String configFileContentType;
    @Lob
    private byte[] firmwareFile;

    private String firmwareFileContentType;

    private Set<AdditionalOptionDTO> additionalOptions = new HashSet<>();

    private Long deviceTypeId;

    private String deviceTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsConfigurable() {
        return isConfigurable;
    }

    public void setIsConfigurable(Boolean isConfigurable) {
        this.isConfigurable = isConfigurable;
    }

    public Integer getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(Integer linesCount) {
        this.linesCount = linesCount;
    }

    public byte[] getConfigFile() {
        return configFile;
    }

    public void setConfigFile(byte[] configFile) {
        this.configFile = configFile;
    }

    public String getConfigFileContentType() {
        return configFileContentType;
    }

    public void setConfigFileContentType(String configFileContentType) {
        this.configFileContentType = configFileContentType;
    }

    public byte[] getFirmwareFile() {
        return firmwareFile;
    }

    public void setFirmwareFile(byte[] firmwareFile) {
        this.firmwareFile = firmwareFile;
    }

    public String getFirmwareFileContentType() {
        return firmwareFileContentType;
    }

    public void setFirmwareFileContentType(String firmwareFileContentType) {
        this.firmwareFileContentType = firmwareFileContentType;
    }

    public Set<AdditionalOptionDTO> getAdditionalOptions() {
        return additionalOptions;
    }

    public void setAdditionalOptions(Set<AdditionalOptionDTO> additionalOptions) {
        this.additionalOptions = additionalOptions;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeviceModelDTO deviceModelDTO = (DeviceModelDTO) o;
        if (deviceModelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceModelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceModelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isConfigurable='" + isIsConfigurable() + "'" +
            ", linesCount=" + getLinesCount() +
            ", configFile='" + getConfigFile() + "'" +
            ", firmwareFile='" + getFirmwareFile() + "'" +
            ", deviceType=" + getDeviceTypeId() +
            ", deviceType='" + getDeviceTypeName() + "'" +
            "}";
    }
}
