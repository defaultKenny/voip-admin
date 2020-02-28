package com.enotkenny.voipadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.enotkenny.voipadmin.web.rest.TestUtil;

public class SipAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SipAccountDTO.class);
        SipAccountDTO sipAccountDTO1 = new SipAccountDTO();
        sipAccountDTO1.setId(1L);
        SipAccountDTO sipAccountDTO2 = new SipAccountDTO();
        assertThat(sipAccountDTO1).isNotEqualTo(sipAccountDTO2);
        sipAccountDTO2.setId(sipAccountDTO1.getId());
        assertThat(sipAccountDTO1).isEqualTo(sipAccountDTO2);
        sipAccountDTO2.setId(2L);
        assertThat(sipAccountDTO1).isNotEqualTo(sipAccountDTO2);
        sipAccountDTO1.setId(null);
        assertThat(sipAccountDTO1).isNotEqualTo(sipAccountDTO2);
    }
}
