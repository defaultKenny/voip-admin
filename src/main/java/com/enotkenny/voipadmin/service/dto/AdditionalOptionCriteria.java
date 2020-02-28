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
 * Criteria class for the {@link com.enotkenny.voipadmin.domain.AdditionalOption} entity. This class is used
 * in {@link com.enotkenny.voipadmin.web.rest.AdditionalOptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /additional-options?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdditionalOptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter description;

    private LongFilter deviceSettingId;

    private LongFilter deviceModelsId;

    public AdditionalOptionCriteria(){
    }

    public AdditionalOptionCriteria(AdditionalOptionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.deviceSettingId = other.deviceSettingId == null ? null : other.deviceSettingId.copy();
        this.deviceModelsId = other.deviceModelsId == null ? null : other.deviceModelsId.copy();
    }

    @Override
    public AdditionalOptionCriteria copy() {
        return new AdditionalOptionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getDeviceSettingId() {
        return deviceSettingId;
    }

    public void setDeviceSettingId(LongFilter deviceSettingId) {
        this.deviceSettingId = deviceSettingId;
    }

    public LongFilter getDeviceModelsId() {
        return deviceModelsId;
    }

    public void setDeviceModelsId(LongFilter deviceModelsId) {
        this.deviceModelsId = deviceModelsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdditionalOptionCriteria that = (AdditionalOptionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(description, that.description) &&
            Objects.equals(deviceSettingId, that.deviceSettingId) &&
            Objects.equals(deviceModelsId, that.deviceModelsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        description,
        deviceSettingId,
        deviceModelsId
        );
    }

    @Override
    public String toString() {
        return "AdditionalOptionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (deviceSettingId != null ? "deviceSettingId=" + deviceSettingId + ", " : "") +
                (deviceModelsId != null ? "deviceModelsId=" + deviceModelsId + ", " : "") +
            "}";
    }

}
