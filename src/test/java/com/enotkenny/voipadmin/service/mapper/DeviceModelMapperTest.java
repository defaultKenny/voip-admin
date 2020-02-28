package com.enotkenny.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DeviceModelMapperTest {

    private DeviceModelMapper deviceModelMapper;

    @BeforeEach
    public void setUp() {
        deviceModelMapper = new DeviceModelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(deviceModelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(deviceModelMapper.fromId(null)).isNull();
    }
}
