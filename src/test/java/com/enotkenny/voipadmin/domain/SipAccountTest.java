package com.enotkenny.voipadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.enotkenny.voipadmin.web.rest.TestUtil;

public class SipAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SipAccount.class);
        SipAccount sipAccount1 = new SipAccount();
        sipAccount1.setId(1L);
        SipAccount sipAccount2 = new SipAccount();
        sipAccount2.setId(sipAccount1.getId());
        assertThat(sipAccount1).isEqualTo(sipAccount2);
        sipAccount2.setId(2L);
        assertThat(sipAccount1).isNotEqualTo(sipAccount2);
        sipAccount1.setId(null);
        assertThat(sipAccount1).isNotEqualTo(sipAccount2);
    }
}
