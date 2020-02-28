package com.enotkenny.voipadmin.service.mapper;

import com.enotkenny.voipadmin.domain.*;
import com.enotkenny.voipadmin.service.dto.SipAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SipAccount} and its DTO {@link SipAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {PbxAccountMapper.class, DeviceMapper.class})
public interface SipAccountMapper extends EntityMapper<SipAccountDTO, SipAccount> {

    @Mapping(source = "pbxAccount.id", target = "pbxAccountId")
    @Mapping(source = "device.id", target = "deviceId")
    SipAccountDTO toDto(SipAccount sipAccount);

    @Mapping(source = "pbxAccountId", target = "pbxAccount")
    @Mapping(source = "deviceId", target = "device")
    SipAccount toEntity(SipAccountDTO sipAccountDTO);

    default SipAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        SipAccount sipAccount = new SipAccount();
        sipAccount.setId(id);
        return sipAccount;
    }
}
