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
 * Criteria class for the {@link com.enotkenny.voipadmin.domain.SipAccount} entity. This class is used
 * in {@link com.enotkenny.voipadmin.web.rest.SipAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sip-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SipAccountCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter username;

    private StringFilter password;

    private BooleanFilter lineEnabled;

    private IntegerFilter lineNumber;

    private BooleanFilter isManuallyCreated;

    private LongFilter pbxAccountId;

    private LongFilter deviceId;

    public SipAccountCriteria(){
    }

    public SipAccountCriteria(SipAccountCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.lineEnabled = other.lineEnabled == null ? null : other.lineEnabled.copy();
        this.lineNumber = other.lineNumber == null ? null : other.lineNumber.copy();
        this.isManuallyCreated = other.isManuallyCreated == null ? null : other.isManuallyCreated.copy();
        this.pbxAccountId = other.pbxAccountId == null ? null : other.pbxAccountId.copy();
        this.deviceId = other.deviceId == null ? null : other.deviceId.copy();
    }

    @Override
    public SipAccountCriteria copy() {
        return new SipAccountCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUsername() {
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPassword() {
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public BooleanFilter getLineEnabled() {
        return lineEnabled;
    }

    public void setLineEnabled(BooleanFilter lineEnabled) {
        this.lineEnabled = lineEnabled;
    }

    public IntegerFilter getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(IntegerFilter lineNumber) {
        this.lineNumber = lineNumber;
    }

    public BooleanFilter getIsManuallyCreated() {
        return isManuallyCreated;
    }

    public void setIsManuallyCreated(BooleanFilter isManuallyCreated) {
        this.isManuallyCreated = isManuallyCreated;
    }

    public LongFilter getPbxAccountId() {
        return pbxAccountId;
    }

    public void setPbxAccountId(LongFilter pbxAccountId) {
        this.pbxAccountId = pbxAccountId;
    }

    public LongFilter getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(LongFilter deviceId) {
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
        final SipAccountCriteria that = (SipAccountCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(lineEnabled, that.lineEnabled) &&
            Objects.equals(lineNumber, that.lineNumber) &&
            Objects.equals(isManuallyCreated, that.isManuallyCreated) &&
            Objects.equals(pbxAccountId, that.pbxAccountId) &&
            Objects.equals(deviceId, that.deviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        username,
        password,
        lineEnabled,
        lineNumber,
        isManuallyCreated,
        pbxAccountId,
        deviceId
        );
    }

    @Override
    public String toString() {
        return "SipAccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
                (lineEnabled != null ? "lineEnabled=" + lineEnabled + ", " : "") +
                (lineNumber != null ? "lineNumber=" + lineNumber + ", " : "") +
                (isManuallyCreated != null ? "isManuallyCreated=" + isManuallyCreated + ", " : "") +
                (pbxAccountId != null ? "pbxAccountId=" + pbxAccountId + ", " : "") +
                (deviceId != null ? "deviceId=" + deviceId + ", " : "") +
            "}";
    }

}
