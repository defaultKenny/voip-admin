package com.enotkenny.voipadmin.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ResponsiblePersonMapperTest {

    private ResponsiblePersonMapper responsiblePersonMapper;

    @BeforeEach
    public void setUp() {
        responsiblePersonMapper = new ResponsiblePersonMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(responsiblePersonMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(responsiblePersonMapper.fromId(null)).isNull();
    }
}
