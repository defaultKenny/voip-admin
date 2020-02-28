package com.enotkenny.voipadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.enotkenny.voipadmin.web.rest.TestUtil;

public class PbxAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PbxAccountDTO.class);
        PbxAccountDTO pbxAccountDTO1 = new PbxAccountDTO();
        pbxAccountDTO1.setId(1L);
        PbxAccountDTO pbxAccountDTO2 = new PbxAccountDTO();
        assertThat(pbxAccountDTO1).isNotEqualTo(pbxAccountDTO2);
        pbxAccountDTO2.setId(pbxAccountDTO1.getId());
        assertThat(pbxAccountDTO1).isEqualTo(pbxAccountDTO2);
        pbxAccountDTO2.setId(2L);
        assertThat(pbxAccountDTO1).isNotEqualTo(pbxAccountDTO2);
        pbxAccountDTO1.setId(null);
        assertThat(pbxAccountDTO1).isNotEqualTo(pbxAccountDTO2);
    }
}
