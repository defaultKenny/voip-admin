package com.enotkenny.voipadmin.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.enotkenny.voipadmin.web.rest.TestUtil;

public class ResponsiblePersonDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResponsiblePersonDTO.class);
        ResponsiblePersonDTO responsiblePersonDTO1 = new ResponsiblePersonDTO();
        responsiblePersonDTO1.setId(1L);
        ResponsiblePersonDTO responsiblePersonDTO2 = new ResponsiblePersonDTO();
        assertThat(responsiblePersonDTO1).isNotEqualTo(responsiblePersonDTO2);
        responsiblePersonDTO2.setId(responsiblePersonDTO1.getId());
        assertThat(responsiblePersonDTO1).isEqualTo(responsiblePersonDTO2);
        responsiblePersonDTO2.setId(2L);
        assertThat(responsiblePersonDTO1).isNotEqualTo(responsiblePersonDTO2);
        responsiblePersonDTO1.setId(null);
        assertThat(responsiblePersonDTO1).isNotEqualTo(responsiblePersonDTO2);
    }
}
