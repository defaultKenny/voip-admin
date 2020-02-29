package com.enotkenny.voipadmin.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A DeviceSetting.
 */
@Entity
@Table(name = "device_setting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeviceSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    private AdditionalOption additionalOption;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public DeviceSetting value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Device getDevice() {
        return device;
    }

    public DeviceSetting device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public AdditionalOption getAdditionalOption() {
        return additionalOption;
    }

    public DeviceSetting additionalOption(AdditionalOption additionalOption) {
        this.additionalOption = additionalOption;
        return this;
    }

    public void setAdditionalOption(AdditionalOption additionalOption) {
        this.additionalOption = additionalOption;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceSetting)) {
            return false;
        }
        return id != null && id.equals(((DeviceSetting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeviceSetting{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
