package com.enotkenny.voipadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.enotkenny.voipadmin.web.rest.TestUtil;

public class AdditionalOptionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalOptionDTO.class);
        AdditionalOptionDTO additionalOptionDTO1 = new AdditionalOptionDTO();
        additionalOptionDTO1.setId(1L);
        AdditionalOptionDTO additionalOptionDTO2 = new AdditionalOptionDTO();
        assertThat(additionalOptionDTO1).isNotEqualTo(additionalOptionDTO2);
        additionalOptionDTO2.setId(additionalOptionDTO1.getId());
        assertThat(additionalOptionDTO1).isEqualTo(additionalOptionDTO2);
        additionalOptionDTO2.setId(2L);
        assertThat(additionalOptionDTO1).isNotEqualTo(additionalOptionDTO2);
        additionalOptionDTO1.setId(null);
        assertThat(additionalOptionDTO1).isNotEqualTo(additionalOptionDTO2);
    }
}
