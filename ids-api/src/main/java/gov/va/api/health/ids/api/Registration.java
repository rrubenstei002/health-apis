package gov.va.api.health.ids.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.List;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Registration {
  String uuid;
  @Singular List<ResourceIdentity> resourceIdentities;
}
