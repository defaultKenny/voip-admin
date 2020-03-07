package com.enotkenny.voipadmin.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.enotkenny.voipadmin.domain.enumeration.ProvProtocol;

/**
 * A Device.
 */
@Entity
@Table(name = "device")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "mac", nullable = false)
    private String mac;

    @NotNull
    @Column(name = "inventory", nullable = false)
    private String inventory;

    @Column(name = "location")
    private String location;

    @Column(name = "hostname")
    private String hostname;

    @Column(name = "web_access_login")
    private String webAccessLogin;

    @Column(name = "web_access_password")
    private String webAccessPassword;

    @Column(name = "dhcp_enabled")
    private Boolean dhcpEnabled;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "subnet_mask")
    private String subnetMask;

    @Column(name = "default_gateway")
    private String defaultGateway;

    @Column(name = "dns_1")
    private String dns1;

    @Column(name = "dns_2")
    private String dns2;

    @Column(name = "prov_url")
    private String provUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "prov_protocol")
    private ProvProtocol provProtocol;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "device_id")
    private Set<SipAccount> sipAccounts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "device_id")
    private Set<DeviceSetting> deviceSettings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JsonIgnoreProperties("devices")
    private DeviceModel deviceModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("devices")
    private ResponsiblePerson responsiblePerson;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public Device mac(String mac) {
        this.mac = mac;
        return this;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getInventory() {
        return inventory;
    }

    public Device inventory(String inventory) {
        this.inventory = inventory;
        return this;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getLocation() {
        return location;
    }

    public Device location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHostname() {
        return hostname;
    }

    public Device hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getWebAccessLogin() {
        return webAccessLogin;
    }

    public Device webAccessLogin(String webAccessLogin) {
        this.webAccessLogin = webAccessLogin;
        return this;
    }

    public void setWebAccessLogin(String webAccessLogin) {
        this.webAccessLogin = webAccessLogin;
    }

    public String getWebAccessPassword() {
        return webAccessPassword;
    }

    public Device webAccessPassword(String webAccessPassword) {
        this.webAccessPassword = webAccessPassword;
        return this;
    }

    public void setWebAccessPassword(String webAccessPassword) {
        this.webAccessPassword = webAccessPassword;
    }

    public Boolean isDhcpEnabled() {
        return dhcpEnabled;
    }

    public Device dhcpEnabled(Boolean dhcpEnabled) {
        this.dhcpEnabled = dhcpEnabled;
        return this;
    }

    public void setDhcpEnabled(Boolean dhcpEnabled) {
        this.dhcpEnabled = dhcpEnabled;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Device ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public Device subnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
        return this;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public String getDefaultGateway() {
        return defaultGateway;
    }

    public Device defaultGateway(String defaultGateway) {
        this.defaultGateway = defaultGateway;
        return this;
    }

    public void setDefaultGateway(String defaultGateway) {
        this.defaultGateway = defaultGateway;
    }

    public String getDns1() {
        return dns1;
    }

    public Device dns1(String dns1) {
        this.dns1 = dns1;
        return this;
    }

    public void setDns1(String dns1) {
        this.dns1 = dns1;
    }

    public String getDns2() {
        return dns2;
    }

    public Device dns2(String dns2) {
        this.dns2 = dns2;
        return this;
    }

    public void setDns2(String dns2) {
        this.dns2 = dns2;
    }

    public String getProvUrl() {
        return provUrl;
    }

    public Device provUrl(String provUrl) {
        this.provUrl = provUrl;
        return this;
    }

    public void setProvUrl(String provUrl) {
        this.provUrl = provUrl;
    }

    public ProvProtocol getProvProtocol() {
        return provProtocol;
    }

    public Device provProtocol(ProvProtocol provProtocol) {
        this.provProtocol = provProtocol;
        return this;
    }

    public void setProvProtocol(ProvProtocol provProtocol) {
        this.provProtocol = provProtocol;
    }

    public Set<SipAccount> getSipAccounts() {
        return sipAccounts;
    }

    public Device sipAccounts(Set<SipAccount> sipAccounts) {
        this.sipAccounts = sipAccounts;
        return this;
    }

    public Device addSipAccount(SipAccount sipAccount) {
        this.sipAccounts.add(sipAccount);
        sipAccount.setDevice(this);
        return this;
    }

    public Device removeSipAccount(SipAccount sipAccount) {
        this.sipAccounts.remove(sipAccount);
        sipAccount.setDevice(null);
        return this;
    }

    public void setSipAccounts(Set<SipAccount> sipAccounts) {
        this.sipAccounts = sipAccounts;
    }

    public Set<DeviceSetting> getDeviceSettings() {
        return deviceSettings;
    }

    public Device deviceSettings(Set<DeviceSetting> deviceSettings) {
        this.deviceSettings = deviceSettings;
        return this;
    }

    public Device addDeviceSetting(DeviceSetting deviceSetting) {
        this.deviceSettings.add(deviceSetting);
        deviceSetting.setDevice(this);
        return this;
    }

    public Device removeDeviceSetting(DeviceSetting deviceSetting) {
        this.deviceSettings.remove(deviceSetting);
        deviceSetting.setDevice(null);
        return this;
    }

    public void setDeviceSettings(Set<DeviceSetting> deviceSettings) {
        this.deviceSettings = deviceSettings;
    }

    public DeviceModel getDeviceModel() {
        return deviceModel;
    }

    public Device deviceModel(DeviceModel deviceModel) {
        this.deviceModel = deviceModel;
        return this;
    }

    public void setDeviceModel(DeviceModel deviceModel) {
        this.deviceModel = deviceModel;
    }

    public ResponsiblePerson getResponsiblePerson() {
        return responsiblePerson;
    }

    public Device responsiblePerson(ResponsiblePerson responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
        return this;
    }

    public void setResponsiblePerson(ResponsiblePerson responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Device)) {
            return false;
        }
        return id != null && id.equals(((Device) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Device{" +
            "id=" + getId() +
            ", mac='" + getMac() + "'" +
            ", inventory='" + getInventory() + "'" +
            ", location='" + getLocation() + "'" +
            ", hostname='" + getHostname() + "'" +
            ", webAccessLogin='" + getWebAccessLogin() + "'" +
            ", webAccessPassword='" + getWebAccessPassword() + "'" +
            ", dhcpEnabled='" + isDhcpEnabled() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", subnetMask='" + getSubnetMask() + "'" +
            ", defaultGateway='" + getDefaultGateway() + "'" +
            ", dns1='" + getDns1() + "'" +
            ", dns2='" + getDns2() + "'" +
            ", provUrl='" + getProvUrl() + "'" +
            ", provProtocol='" + getProvProtocol() + "'" +
            "}";
    }
}
