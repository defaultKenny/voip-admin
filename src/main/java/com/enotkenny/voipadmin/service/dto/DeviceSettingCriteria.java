package com.enotkenny.voipadmin.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.enotkenny.voipadmin.domain.DeviceSetting} entity. This class is used
 * in {@link com.enotkenny.voipadmin.web.rest.DeviceSettingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /device-settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeviceSettingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter value;

    private LongFilter deviceId;

    private LongFilter additionalOptionId;

    public DeviceSettingCriteria(){
    }

    public DeviceSettingCriteria(DeviceSettingCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.deviceId = other.deviceId == null ? null : other.deviceId.copy();
        this.additionalOptionId = other.additionalOptionId == null ? null : other.additionalOptionId.copy();
    }

    @Override
    public DeviceSettingCriteria copy() {
        return new DeviceSettingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public LongFilter getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(LongFilter deviceId) {
        this.deviceId = deviceId;
    }

    public LongFilter getAdditionalOptionId() {
        return additionalOptionId;
    }

    public void setAdditionalOptionId(LongFilter additionalOptionId) {
        this.additionalOptionId = additionalOptionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeviceSettingCriteria that = (DeviceSettingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(deviceId, that.deviceId) &&
            Objects.equals(additionalOptionId, that.additionalOptionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        value,
        deviceId,
        additionalOptionId
        );
    }

    @Override
    public String toString() {
        return "DeviceSettingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (deviceId != null ? "deviceId=" + deviceId + ", " : "") +
                (additionalOptionId != null ? "additionalOptionId=" + additionalOptionId + ", " : "") +
            "}";
    }

}
