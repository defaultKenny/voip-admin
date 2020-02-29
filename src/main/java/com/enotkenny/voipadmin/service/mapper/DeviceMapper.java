package com.enotkenny.voipadmin.service.mapper;

import com.enotkenny.voipadmin.domain.*;
import com.enotkenny.voipadmin.service.dto.DeviceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Device} and its DTO {@link DeviceDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeviceModelMapper.class, ResponsiblePersonMapper.class,
    DeviceSettingMapper.class})
public interface DeviceMapper extends EntityMapper<DeviceDTO, Device> {

    @Mapping(source = "deviceModel.id", target = "deviceModelId")
    @Mapping(source = "deviceModel.name", target = "deviceModelName")
    @Mapping(source = "responsiblePerson.id", target = "responsiblePersonId")
    @Mapping(source = "deviceSettings", target = "deviceSettingDTOs")
    DeviceDTO toDto(Device device);

    @Mapping(target = "sipAccounts", ignore = true)
    @Mapping(target = "removeSipAccount", ignore = true)
    @Mapping(target = "removeDeviceSetting", ignore = true)
    @Mapping(source = "deviceModelId", target = "deviceModel")
    @Mapping(source = "responsiblePersonId", target = "responsiblePerson")
    @Mapping(source = "deviceSettingDTOs", target = "deviceSettings")
    Device toEntity(DeviceDTO deviceDTO);

    default Device fromId(Long id) {
        if (id == null) {
            return null;
        }
        Device device = new Device();
        device.setId(id);
        return device;
    }
}
