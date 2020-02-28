package com.enotkenny.voipadmin.service.mapper;

import com.enotkenny.voipadmin.domain.*;
import com.enotkenny.voipadmin.service.dto.DeviceTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeviceType} and its DTO {@link DeviceTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeviceTypeMapper extends EntityMapper<DeviceTypeDTO, DeviceType> {


    @Mapping(target = "deviceModels", ignore = true)
    @Mapping(target = "removeDeviceModel", ignore = true)
    DeviceType toEntity(DeviceTypeDTO deviceTypeDTO);

    default DeviceType fromId(Long id) {
        if (id == null) {
            return null;
        }
        DeviceType deviceType = new DeviceType();
        deviceType.setId(id);
        return deviceType;
    }
}
