package com.enotkenny.voipadmin.service.mapper;

import com.enotkenny.voipadmin.domain.*;
import com.enotkenny.voipadmin.service.dto.DeviceSettingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeviceSetting} and its DTO {@link DeviceSettingDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeviceMapper.class, AdditionalOptionMapper.class})
public interface DeviceSettingMapper extends EntityMapper<DeviceSettingDTO, DeviceSetting> {

    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(source = "device.mac", target = "deviceMac")
    @Mapping(source = "additionalOption.id", target = "additionalOptionId")
    @Mapping(source = "additionalOption.code", target = "additionalOptionCode")
    DeviceSettingDTO toDto(DeviceSetting deviceSetting);

    @Mapping(source = "deviceId", target = "device")
    @Mapping(source = "additionalOptionId", target = "additionalOption")
    DeviceSetting toEntity(DeviceSettingDTO deviceSettingDTO);

    default DeviceSetting fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeviceSetting deviceSetting = new DeviceSetting();
        deviceSetting.setId(id);
        return deviceSetting;
    }
}
