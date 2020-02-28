package com.enotkenny.voipadmin.service.mapper;

import com.enotkenny.voipadmin.domain.*;
import com.enotkenny.voipadmin.service.dto.PbxAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PbxAccount} and its DTO {@link PbxAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PbxAccountMapper extends EntityMapper<PbxAccountDTO, PbxAccount> {


    @Mapping(target = "sipAccount", ignore = true)
    PbxAccount toEntity(PbxAccountDTO pbxAccountDTO);

    default PbxAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        PbxAccount pbxAccount = new PbxAccount();
        pbxAccount.setId(id);
        return pbxAccount;
    }
}
