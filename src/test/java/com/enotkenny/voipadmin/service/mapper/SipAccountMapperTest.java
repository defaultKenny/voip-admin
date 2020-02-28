package com.enotkenny.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class SipAccountMapperTest {

    private SipAccountMapper sipAccountMapper;

    @BeforeEach
    public void setUp() {
        sipAccountMapper = new SipAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(sipAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sipAccountMapper.fromId(null)).isNull();
    }
}
