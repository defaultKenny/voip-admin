package com.enotkenny.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DeviceSettingMapperTest {

    private DeviceSettingMapper deviceSettingMapper;

    @BeforeEach
    public void setUp() {
        deviceSettingMapper = new DeviceSettingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(deviceSettingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(deviceSettingMapper.fromId(null)).isNull();
    }
}
