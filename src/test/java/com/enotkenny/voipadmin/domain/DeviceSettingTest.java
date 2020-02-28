package com.enotkenny.voipadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.enotkenny.voipadmin.web.rest.TestUtil;

public class DeviceSettingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeviceSetting.class);
        DeviceSetting deviceSetting1 = new DeviceSetting();
        deviceSetting1.setId(1L);
        DeviceSetting deviceSetting2 = new DeviceSetting();
        deviceSetting2.setId(deviceSetting1.getId());
        assertThat(deviceSetting1).isEqualTo(deviceSetting2);
        deviceSetting2.setId(2L);
        assertThat(deviceSetting1).isNotEqualTo(deviceSetting2);
        deviceSetting1.setId(null);
        assertThat(deviceSetting1).isNotEqualTo(deviceSetting2);
    }
}
