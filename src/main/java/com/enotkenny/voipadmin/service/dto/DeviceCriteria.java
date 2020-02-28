package com.enotkenny.voipadmin.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.enotkenny.voipadmin.domain.enumeration.ProvProtocol;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.enotkenny.voipadmin.domain.Device} entity. This class is used
 * in {@link com.enotkenny.voipadmin.web.rest.DeviceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /devices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DeviceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ProvProtocol
     */
    public static class ProvProtocolFilter extends Filter<ProvProtocol> {

        public ProvProtocolFilter() {
        }

        public ProvProtocolFilter(ProvProtocolFilter filter) {
            super(filter);
        }

        @Override
        public ProvProtocolFilter copy() {
            return new ProvProtocolFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter mac;

    private StringFilter inventory;

    private StringFilter location;

    private StringFilter hostname;

    private StringFilter webAccessLogin;

    private StringFilter webAccessPassword;

    private BooleanFilter dhcpEnabled;

    private StringFilter ipAddress;

    private StringFilter subnetMask;

    private StringFilter defaultGateway;

    private StringFilter dns1;

    private StringFilter dns2;

    private StringFilter provUrl;

    private ProvProtocolFilter provProtocol;

    private LongFilter sipAccountId;

    private LongFilter deviceSettingId;

    private LongFilter deviceModelId;

    private LongFilter responsiblePersonId;

    public DeviceCriteria(){
    }

    public DeviceCriteria(DeviceCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.mac = other.mac == null ? null : other.mac.copy();
        this.inventory = other.inventory == null ? null : other.inventory.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.hostname = other.hostname == null ? null : other.hostname.copy();
        this.webAccessLogin = other.webAccessLogin == null ? null : other.webAccessLogin.copy();
        this.webAccessPassword = other.webAccessPassword == null ? null : other.webAccessPassword.copy();
        this.dhcpEnabled = other.dhcpEnabled == null ? null : other.dhcpEnabled.copy();
        this.ipAddress = other.ipAddress == null ? null : other.ipAddress.copy();
        this.subnetMask = other.subnetMask == null ? null : other.subnetMask.copy();
        this.defaultGateway = other.defaultGateway == null ? null : other.defaultGateway.copy();
        this.dns1 = other.dns1 == null ? null : other.dns1.copy();
        this.dns2 = other.dns2 == null ? null : other.dns2.copy();
        this.provUrl = other.provUrl == null ? null : other.provUrl.copy();
        this.provProtocol = other.provProtocol == null ? null : other.provProtocol.copy();
        this.sipAccountId = other.sipAccountId == null ? null : other.sipAccountId.copy();
        this.deviceSettingId = other.deviceSettingId == null ? null : other.deviceSettingId.copy();
        this.deviceModelId = other.deviceModelId == null ? null : other.deviceModelId.copy();
        this.responsiblePersonId = other.responsiblePersonId == null ? null : other.responsiblePersonId.copy();
    }

    @Override
    public DeviceCriteria copy() {
        return new DeviceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMac() {
        return mac;
    }

    public void setMac(StringFilter mac) {
        this.mac = mac;
    }

    public StringFilter getInventory() {
        return inventory;
    }

    public void setInventory(StringFilter inventory) {
        this.inventory = inventory;
    }

    public StringFilter getLocation() {
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public StringFilter getHostname() {
        return hostname;
    }

    public void setHostname(StringFilter hostname) {
        this.hostname = hostname;
    }

    public StringFilter getWebAccessLogin() {
        return webAccessLogin;
    }

    public void setWebAccessLogin(StringFilter webAccessLogin) {
        this.webAccessLogin = webAccessLogin;
    }

    public StringFilter getWebAccessPassword() {
        return webAccessPassword;
    }

    public void setWebAccessPassword(StringFilter webAccessPassword) {
        this.webAccessPassword = webAccessPassword;
    }

    public BooleanFilter getDhcpEnabled() {
        return dhcpEnabled;
    }

    public void setDhcpEnabled(BooleanFilter dhcpEnabled) {
        this.dhcpEnabled = dhcpEnabled;
    }

    public StringFilter getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(StringFilter ipAddress) {
        this.ipAddress = ipAddress;
    }

    public StringFilter getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(StringFilter subnetMask) {
        this.subnetMask = subnetMask;
    }

    public StringFilter getDefaultGateway() {
        return defaultGateway;
    }

    public void setDefaultGateway(StringFilter defaultGateway) {
        this.defaultGateway = defaultGateway;
    }

    public StringFilter getDns1() {
        return dns1;
    }

    public void setDns1(StringFilter dns1) {
        this.dns1 = dns1;
    }

    public StringFilter getDns2() {
        return dns2;
    }

    public void setDns2(StringFilter dns2) {
        this.dns2 = dns2;
    }

    public StringFilter getProvUrl() {
        return provUrl;
    }

    public void setProvUrl(StringFilter provUrl) {
        this.provUrl = provUrl;
    }

    public ProvProtocolFilter getProvProtocol() {
        return provProtocol;
    }

    public void setProvProtocol(ProvProtocolFilter provProtocol) {
        this.provProtocol = provProtocol;
    }

    public LongFilter getSipAccountId() {
        return sipAccountId;
    }

    public void setSipAccountId(LongFilter sipAccountId) {
        this.sipAccountId = sipAccountId;
    }

    public LongFilter getDeviceSettingId() {
        return deviceSettingId;
    }

    public void setDeviceSettingId(LongFilter deviceSettingId) {
        this.deviceSettingId = deviceSettingId;
    }

    public LongFilter getDeviceModelId() {
        return deviceModelId;
    }

    public void setDeviceModelId(LongFilter deviceModelId) {
        this.deviceModelId = deviceModelId;
    }

    public LongFilter getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(LongFilter responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DeviceCriteria that = (DeviceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(mac, that.mac) &&
            Objects.equals(inventory, that.inventory) &&
            Objects.equals(location, that.location) &&
            Objects.equals(hostname, that.hostname) &&
            Objects.equals(webAccessLogin, that.webAccessLogin) &&
            Objects.equals(webAccessPassword, that.webAccessPassword) &&
            Objects.equals(dhcpEnabled, that.dhcpEnabled) &&
            Objects.equals(ipAddress, that.ipAddress) &&
            Objects.equals(subnetMask, that.subnetMask) &&
            Objects.equals(defaultGateway, that.defaultGateway) &&
            Objects.equals(dns1, that.dns1) &&
            Objects.equals(dns2, that.dns2) &&
            Objects.equals(provUrl, that.provUrl) &&
            Objects.equals(provProtocol, that.provProtocol) &&
            Objects.equals(sipAccountId, that.sipAccountId) &&
            Objects.equals(deviceSettingId, that.deviceSettingId) &&
            Objects.equals(deviceModelId, that.deviceModelId) &&
            Objects.equals(responsiblePersonId, that.responsiblePersonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        mac,
        inventory,
        location,
        hostname,
        webAccessLogin,
        webAccessPassword,
        dhcpEnabled,
        ipAddress,
        subnetMask,
        defaultGateway,
        dns1,
        dns2,
        provUrl,
        provProtocol,
        sipAccountId,
        deviceSettingId,
        deviceModelId,
        responsiblePersonId
        );
    }

    @Override
    public String toString() {
        return "DeviceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (mac != null ? "mac=" + mac + ", " : "") +
                (inventory != null ? "inventory=" + inventory + ", " : "") +
                (location != null ? "location=" + location + ", " : "") +
                (hostname != null ? "hostname=" + hostname + ", " : "") +
                (webAccessLogin != null ? "webAccessLogin=" + webAccessLogin + ", " : "") +
                (webAccessPassword != null ? "webAccessPassword=" + webAccessPassword + ", " : "") +
                (dhcpEnabled != null ? "dhcpEnabled=" + dhcpEnabled + ", " : "") +
                (ipAddress != null ? "ipAddress=" + ipAddress + ", " : "") +
                (subnetMask != null ? "subnetMask=" + subnetMask + ", " : "") +
                (defaultGateway != null ? "defaultGateway=" + defaultGateway + ", " : "") +
                (dns1 != null ? "dns1=" + dns1 + ", " : "") +
                (dns2 != null ? "dns2=" + dns2 + ", " : "") +
                (provUrl != null ? "provUrl=" + provUrl + ", " : "") +
                (provProtocol != null ? "provProtocol=" + provProtocol + ", " : "") +
                (sipAccountId != null ? "sipAccountId=" + sipAccountId + ", " : "") +
                (deviceSettingId != null ? "deviceSettingId=" + deviceSettingId + ", " : "") +
                (deviceModelId != null ? "deviceModelId=" + deviceModelId + ", " : "") +
                (responsiblePersonId != null ? "responsiblePersonId=" + responsiblePersonId + ", " : "") +
            "}";
    }

}
