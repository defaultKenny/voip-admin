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
 * Criteria class for the {@link com.enotkenny.voipadmin.domain.PbxAccount} entity. This class is used
 * in {@link com.enotkenny.voipadmin.web.rest.PbxAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pbx-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PbxAccountCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter username;

    private StringFilter pbxId;

    private LongFilter sipAccountId;

    public PbxAccountCriteria(){
    }

    public PbxAccountCriteria(PbxAccountCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.pbxId = other.pbxId == null ? null : other.pbxId.copy();
        this.sipAccountId = other.sipAccountId == null ? null : other.sipAccountId.copy();
    }

    @Override
    public PbxAccountCriteria copy() {
        return new PbxAccountCriteria(this);
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

    public StringFilter getPbxId() {
        return pbxId;
    }

    public void setPbxId(StringFilter pbxId) {
        this.pbxId = pbxId;
    }

    public LongFilter getSipAccountId() {
        return sipAccountId;
    }

    public void setSipAccountId(LongFilter sipAccountId) {
        this.sipAccountId = sipAccountId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PbxAccountCriteria that = (PbxAccountCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(username, that.username) &&
            Objects.equals(pbxId, that.pbxId) &&
            Objects.equals(sipAccountId, that.sipAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        username,
        pbxId,
        sipAccountId
        );
    }

    @Override
    public String toString() {
        return "PbxAccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (pbxId != null ? "pbxId=" + pbxId + ", " : "") +
                (sipAccountId != null ? "sipAccountId=" + sipAccountId + ", " : "") +
            "}";
    }

}
