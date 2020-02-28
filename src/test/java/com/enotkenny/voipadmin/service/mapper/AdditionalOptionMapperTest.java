package com.enotkenny.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class AdditionalOptionMapperTest {

    private AdditionalOptionMapper additionalOptionMapper;

    @BeforeEach
    public void setUp() {
        additionalOptionMapper = new AdditionalOptionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(additionalOptionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(additionalOptionMapper.fromId(null)).isNull();
    }
}
