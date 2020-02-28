package com.enotkenny.voipadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.enotkenny.voipadmin.web.rest.TestUtil;

public class DeviceSettingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceSettingDTO.class);
        DeviceSettingDTO deviceSettingDTO1 = new DeviceSettingDTO();
        deviceSettingDTO1.setId(1L);
        DeviceSettingDTO deviceSettingDTO2 = new DeviceSettingDTO();
        assertThat(deviceSettingDTO1).isNotEqualTo(deviceSettingDTO2);
        deviceSettingDTO2.setId(deviceSettingDTO1.getId());
        assertThat(deviceSettingDTO1).isEqualTo(deviceSettingDTO2);
        deviceSettingDTO2.setId(2L);
        assertThat(deviceSettingDTO1).isNotEqualTo(deviceSettingDTO2);
        deviceSettingDTO1.setId(null);
        assertThat(deviceSettingDTO1).isNotEqualTo(deviceSettingDTO2);
    }
}
