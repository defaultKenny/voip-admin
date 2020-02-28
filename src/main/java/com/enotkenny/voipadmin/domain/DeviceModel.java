package com.enotkenny.voipadmin.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeviceModel.
 */
@Entity
@Table(name = "device_model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeviceModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_configurable")
    private Boolean isConfigurable;

    @Column(name = "lines_count")
    private Integer linesCount;

    @Lob
    @Column(name = "config_file")
    private byte[] configFile;

    @Column(name = "config_file_content_type")
    private String configFileContentType;

    @Lob
    @Column(name = "firmware_file")
    private byte[] firmwareFile;

    @Column(name = "firmware_file_content_type")
    private String firmwareFileContentType;

    @OneToMany(mappedBy = "deviceModel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Device> devices = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "device_model_additional_options",
               joinColumns = @JoinColumn(name = "device_model_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "additional_options_id", referencedColumnName = "id"))
    private Set<AdditionalOption> additionalOptions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JsonIgnoreProperties("deviceModels")
    private DeviceType deviceType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DeviceModel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsConfigurable() {
        return isConfigurable;
    }

    public DeviceModel isConfigurable(Boolean isConfigurable) {
        this.isConfigurable = isConfigurable;
        return this;
    }

    public void setIsConfigurable(Boolean isConfigurable) {
        this.isConfigurable = isConfigurable;
    }

    public Integer getLinesCount() {
        return linesCount;
    }

    public DeviceModel linesCount(Integer linesCount) {
        this.linesCount = linesCount;
        return this;
    }

    public void setLinesCount(Integer linesCount) {
        this.linesCount = linesCount;
    }

    public byte[] getConfigFile() {
        return configFile;
    }

    public DeviceModel configFile(byte[] configFile) {
        this.configFile = configFile;
        return this;
    }

    public void setConfigFile(byte[] configFile) {
        this.configFile = configFile;
    }

    public String getConfigFileContentType() {
        return configFileContentType;
    }

    public DeviceModel configFileContentType(String configFileContentType) {
        this.configFileContentType = configFileContentType;
        return this;
    }

    public void setConfigFileContentType(String configFileContentType) {
        this.configFileContentType = configFileContentType;
    }

    public byte[] getFirmwareFile() {
        return firmwareFile;
    }

    public DeviceModel firmwareFile(byte[] firmwareFile) {
        this.firmwareFile = firmwareFile;
        return this;
    }

    public void setFirmwareFile(byte[] firmwareFile) {
        this.firmwareFile = firmwareFile;
    }

    public String getFirmwareFileContentType() {
        return firmwareFileContentType;
    }

    public DeviceModel firmwareFileContentType(String firmwareFileContentType) {
        this.firmwareFileContentType = firmwareFileContentType;
        return this;
    }

    public void setFirmwareFileContentType(String firmwareFileContentType) {
        this.firmwareFileContentType = firmwareFileContentType;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public DeviceModel devices(Set<Device> devices) {
        this.devices = devices;
        return this;
    }

    public DeviceModel addDevice(Device device) {
        this.devices.add(device);
        device.setDeviceModel(this);
        return this;
    }

    public DeviceModel removeDevice(Device device) {
        this.devices.remove(device);
        device.setDeviceModel(null);
        return this;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public Set<AdditionalOption> getAdditionalOptions() {
        return additionalOptions;
    }

    public DeviceModel additionalOptions(Set<AdditionalOption> additionalOptions) {
        this.additionalOptions = additionalOptions;
        return this;
    }

    public DeviceModel addAdditionalOptions(AdditionalOption additionalOption) {
        this.additionalOptions.add(additionalOption);
        additionalOption.getDeviceModels().add(this);
        return this;
    }

    public DeviceModel removeAdditionalOptions(AdditionalOption additionalOption) {
        this.additionalOptions.remove(additionalOption);
        additionalOption.getDeviceModels().remove(this);
        return this;
    }

    public void setAdditionalOptions(Set<AdditionalOption> additionalOptions) {
        this.additionalOptions = additionalOptions;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public DeviceModel deviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceModel)) {
            return false;
        }
        return id != null && id.equals(((DeviceModel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeviceModel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isConfigurable='" + isIsConfigurable() + "'" +
            ", linesCount=" + getLinesCount() +
            ", configFile='" + getConfigFile() + "'" +
            ", configFileContentType='" + getConfigFileContentType() + "'" +
            ", firmwareFile='" + getFirmwareFile() + "'" +
            ", firmwareFileContentType='" + getFirmwareFileContentType() + "'" +
            "}";
    }
}
