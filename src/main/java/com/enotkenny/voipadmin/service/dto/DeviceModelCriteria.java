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
 * Criteria class for the {@link com.enotkenny.voipadmin.domain.DeviceModel} entity. This class is used
 * in {@link com.enotkenny.voipadmin.web.rest.DeviceModelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /device-models?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeviceModelCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter isConfigurable;

    private IntegerFilter linesCount;

    private LongFilter deviceId;

    private LongFilter additionalOptionsId;

    private LongFilter deviceTypeId;

    public DeviceModelCriteria(){
    }

    public DeviceModelCriteria(DeviceModelCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.isConfigurable = other.isConfigurable == null ? null : other.isConfigurable.copy();
        this.linesCount = other.linesCount == null ? null : other.linesCount.copy();
        this.deviceId = other.deviceId == null ? null : other.deviceId.copy();
        this.additionalOptionsId = other.additionalOptionsId == null ? null : other.additionalOptionsId.copy();
        this.deviceTypeId = other.deviceTypeId == null ? null : other.deviceTypeId.copy();
    }

    @Override
    public DeviceModelCriteria copy() {
        return new DeviceModelCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getIsConfigurable() {
        return isConfigurable;
    }

    public void setIsConfigurable(BooleanFilter isConfigurable) {
        this.isConfigurable = isConfigurable;
    }

    public IntegerFilter getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(IntegerFilter linesCount) {
        this.linesCount = linesCount;
    }

    public LongFilter getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(LongFilter deviceId) {
        this.deviceId = deviceId;
    }

    public LongFilter getAdditionalOptionsId() {
        return additionalOptionsId;
    }

    public void setAdditionalOptionsId(LongFilter additionalOptionsId) {
        this.additionalOptionsId = additionalOptionsId;
    }

    public LongFilter getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(LongFilter deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeviceModelCriteria that = (DeviceModelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(isConfigurable, that.isConfigurable) &&
            Objects.equals(linesCount, that.linesCount) &&
            Objects.equals(deviceId, that.deviceId) &&
            Objects.equals(additionalOptionsId, that.additionalOptionsId) &&
            Objects.equals(deviceTypeId, that.deviceTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        isConfigurable,
        linesCount,
        deviceId,
        additionalOptionsId,
        deviceTypeId
        );
    }

    @Override
    public String toString() {
        return "DeviceModelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (isConfigurable != null ? "isConfigurable=" + isConfigurable + ", " : "") +
                (linesCount != null ? "linesCount=" + linesCount + ", " : "") +
                (deviceId != null ? "deviceId=" + deviceId + ", " : "") +
                (additionalOptionsId != null ? "additionalOptionsId=" + additionalOptionsId + ", " : "") +
                (deviceTypeId != null ? "deviceTypeId=" + deviceTypeId + ", " : "") +
            "}";
    }

}
