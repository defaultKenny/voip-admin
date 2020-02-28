package com.enotkenny.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PbxAccountMapperTest {

    private PbxAccountMapper pbxAccountMapper;

    @BeforeEach
    public void setUp() {
        pbxAccountMapper = new PbxAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(pbxAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pbxAccountMapper.fromId(null)).isNull();
    }
}
