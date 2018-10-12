package gov.va.api.health.ids.service.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.api.health.ids.api.ResourceIdentity;
import lombok.SneakyThrows;
import org.junit.Test;

public class JacksonConfigTest {

  @Test
  @SneakyThrows
  public void producesMapperUsableWithResourceIdentities() {
    ObjectMapper mapper = new JacksonConfig().objectMapper();
    ResourceIdentity id =
        ResourceIdentity.builder().identifier("i1").resource("r1").system("s1").build();
    String json = mapper.writeValueAsString(id);
    ResourceIdentity roundTrip = mapper.readValue(json, ResourceIdentity.class);
    assertThat(roundTrip).isEqualTo(id);
  }
}
