package com.enotkenny.voipadmin.service.mapper;

import com.enotkenny.voipadmin.domain.*;
import com.enotkenny.voipadmin.service.dto.DeviceModelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeviceModel} and its DTO {@link DeviceModelDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdditionalOptionMapper.class, DeviceTypeMapper.class})
public interface DeviceModelMapper extends EntityMapper<DeviceModelDTO, DeviceModel> {

    @Mapping(source = "deviceType.id", target = "deviceTypeId")
    @Mapping(source = "deviceType.name", target = "deviceTypeName")
    DeviceModelDTO toDto(DeviceModel deviceModel);

    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "removeDevice", ignore = true)
    @Mapping(target = "removeAdditionalOptions", ignore = true)
    @Mapping(source = "deviceTypeId", target = "deviceType")
    DeviceModel toEntity(DeviceModelDTO deviceModelDTO);

    default DeviceModel fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setId(id);
        return deviceModel;
    }
}
