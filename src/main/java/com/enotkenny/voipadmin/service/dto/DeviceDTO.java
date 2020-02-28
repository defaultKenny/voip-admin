package com.enotkenny.voipadmin.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.enotkenny.voipadmin.domain.enumeration.ProvProtocol;

/**
 * A DTO for the {@link com.enotkenny.voipadmin.domain.Device} entity.
 */
public class DeviceDTO implements Serializable {

    private Long id;

    @NotNull
    private String mac;

    @NotNull
    private String inventory;

    private String location;

    private String hostname;

    private String webAccessLogin;

    private String webAccessPassword;

    private Boolean dhcpEnabled;

    private String ipAddress;

    private String subnetMask;

    private String defaultGateway;

    private String dns1;

    private String dns2;

    private String provUrl;

    private ProvProtocol provProtocol;


    private Long deviceModelId;

    private String deviceModelName;

    private Long responsiblePersonId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getWebAccessLogin() {
        return webAccessLogin;
    }

    public void setWebAccessLogin(String webAccessLogin) {
        this.webAccessLogin = webAccessLogin;
    }

    public String getWebAccessPassword() {
        return webAccessPassword;
    }

    public void setWebAccessPassword(String webAccessPassword) {
        this.webAccessPassword = webAccessPassword;
    }

    public Boolean isDhcpEnabled() {
        return dhcpEnabled;
    }

    public void setDhcpEnabled(Boolean dhcpEnabled) {
        this.dhcpEnabled = dhcpEnabled;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public String getDefaultGateway() {
        return defaultGateway;
    }

    public void setDefaultGateway(String defaultGateway) {
        this.defaultGateway = defaultGateway;
    }

    public String getDns1() {
        return dns1;
    }

    public void setDns1(String dns1) {
        this.dns1 = dns1;
    }

    public String getDns2() {
        return dns2;
    }

    public void setDns2(String dns2) {
        this.dns2 = dns2;
    }

    public String getProvUrl() {
        return provUrl;
    }

    public void setProvUrl(String provUrl) {
        this.provUrl = provUrl;
    }

    public ProvProtocol getProvProtocol() {
        return provProtocol;
    }

    public void setProvProtocol(ProvProtocol provProtocol) {
        this.provProtocol = provProtocol;
    }

    public Long getDeviceModelId() {
        return deviceModelId;
    }

    public void setDeviceModelId(Long deviceModelId) {
        this.deviceModelId = deviceModelId;
    }

    public String getDeviceModelName() {
        return deviceModelName;
    }

    public void setDeviceModelName(String deviceModelName) {
        this.deviceModelName = deviceModelName;
    }

    public Long getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(Long responsiblePersonId) {
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

        DeviceDTO deviceDTO = (DeviceDTO) o;
        if (deviceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deviceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeviceDTO{" +
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
            ", deviceModel=" + getDeviceModelId() +
            ", deviceModel='" + getDeviceModelName() + "'" +
            ", responsiblePerson=" + getResponsiblePersonId() +
            "}";
    }
}
