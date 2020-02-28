package com.enotkenny.voipadmin.service.mapper;

import com.enotkenny.voipadmin.domain.*;
import com.enotkenny.voipadmin.service.dto.ResponsiblePersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResponsiblePerson} and its DTO {@link ResponsiblePersonDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ResponsiblePersonMapper extends EntityMapper<ResponsiblePersonDTO, ResponsiblePerson> {


    @Mapping(target = "devices", ignore = true)
    @Mapping(target = "removeDevice", ignore = true)
    ResponsiblePerson toEntity(ResponsiblePersonDTO responsiblePersonDTO);

    default ResponsiblePerson fromId(Long id) {
        if (id == null) {
            return null;
        }
        ResponsiblePerson responsiblePerson = new ResponsiblePerson();
        responsiblePerson.setId(id);
        return responsiblePerson;
    }
}
