package com.enotkenny.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DeviceTypeMapperTest {

    private DeviceTypeMapper deviceTypeMapper;

    @BeforeEach
    public void setUp() {
        deviceTypeMapper = new DeviceTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(deviceTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(deviceTypeMapper.fromId(null)).isNull();
    }
}
