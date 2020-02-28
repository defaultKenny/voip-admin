package com.enotkenny.voipadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.enotkenny.voipadmin.web.rest.TestUtil;

public class DeviceModelDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceModelDTO.class);
        DeviceModelDTO deviceModelDTO1 = new DeviceModelDTO();
        deviceModelDTO1.setId(1L);
        DeviceModelDTO deviceModelDTO2 = new DeviceModelDTO();
        assertThat(deviceModelDTO1).isNotEqualTo(deviceModelDTO2);
        deviceModelDTO2.setId(deviceModelDTO1.getId());
        assertThat(deviceModelDTO1).isEqualTo(deviceModelDTO2);
        deviceModelDTO2.setId(2L);
        assertThat(deviceModelDTO1).isNotEqualTo(deviceModelDTO2);
        deviceModelDTO1.setId(null);
        assertThat(deviceModelDTO1).isNotEqualTo(deviceModelDTO2);
    }
}
