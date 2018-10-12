package gov.va.api.health.ids.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@JsonDeserialize(builder = Registration.RegistrationBuilder.class)
@Value
@Builder
public class Registration {
  String uuid;
  @Singular List<ResourceIdentity> resourceIdentities;
}
