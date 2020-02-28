package com.enotkenny.voipadmin.service.mapper;

import com.enotkenny.voipadmin.domain.*;
import com.enotkenny.voipadmin.service.dto.AdditionalOptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdditionalOption} and its DTO {@link AdditionalOptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdditionalOptionMapper extends EntityMapper<AdditionalOptionDTO, AdditionalOption> {


    @Mapping(target = "deviceSettings", ignore = true)
    @Mapping(target = "removeDeviceSetting", ignore = true)
    @Mapping(target = "deviceModels", ignore = true)
    @Mapping(target = "removeDeviceModels", ignore = true)
    AdditionalOption toEntity(AdditionalOptionDTO additionalOptionDTO);

    default AdditionalOption fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdditionalOption additionalOption = new AdditionalOption();
        additionalOption.setId(id);
        return additionalOption;
    }
}
