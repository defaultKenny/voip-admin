package com.enotkenny.voipadmin.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AdditionalOption.
 */
@Entity
@Table(name = "additional_option")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdditionalOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "additionalOption")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DeviceSetting> deviceSettings = new HashSet<>();

    @ManyToMany(mappedBy = "additionalOptions")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<DeviceModel> deviceModels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public AdditionalOption code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public AdditionalOption description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<DeviceSetting> getDeviceSettings() {
        return deviceSettings;
    }

    public AdditionalOption deviceSettings(Set<DeviceSetting> deviceSettings) {
        this.deviceSettings = deviceSettings;
        return this;
    }

    public AdditionalOption addDeviceSetting(DeviceSetting deviceSetting) {
        this.deviceSettings.add(deviceSetting);
        deviceSetting.setAdditionalOption(this);
        return this;
    }

    public AdditionalOption removeDeviceSetting(DeviceSetting deviceSetting) {
        this.deviceSettings.remove(deviceSetting);
        deviceSetting.setAdditionalOption(null);
        return this;
    }

    public void setDeviceSettings(Set<DeviceSetting> deviceSettings) {
        this.deviceSettings = deviceSettings;
    }

    public Set<DeviceModel> getDeviceModels() {
        return deviceModels;
    }

    public AdditionalOption deviceModels(Set<DeviceModel> deviceModels) {
        this.deviceModels = deviceModels;
        return this;
    }

    public AdditionalOption addDeviceModels(DeviceModel deviceModel) {
        this.deviceModels.add(deviceModel);
        deviceModel.getAdditionalOptions().add(this);
        return this;
    }

    public AdditionalOption removeDeviceModels(DeviceModel deviceModel) {
        this.deviceModels.remove(deviceModel);
        deviceModel.getAdditionalOptions().remove(this);
        return this;
    }

    public void setDeviceModels(Set<DeviceModel> deviceModels) {
        this.deviceModels = deviceModels;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalOption)) {
            return false;
        }
        return id != null && id.equals(((AdditionalOption) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdditionalOption{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
