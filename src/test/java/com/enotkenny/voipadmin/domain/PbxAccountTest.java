package com.enotkenny.voipadmin.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.enotkenny.voipadmin.web.rest.TestUtil;

public class PbxAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PbxAccount.class);
        PbxAccount pbxAccount1 = new PbxAccount();
        pbxAccount1.setId(1L);
        PbxAccount pbxAccount2 = new PbxAccount();
        pbxAccount2.setId(pbxAccount1.getId());
        assertThat(pbxAccount1).isEqualTo(pbxAccount2);
        pbxAccount2.setId(2L);
        assertThat(pbxAccount1).isNotEqualTo(pbxAccount2);
        pbxAccount1.setId(null);
        assertThat(pbxAccount1).isNotEqualTo(pbxAccount2);
    }
}
