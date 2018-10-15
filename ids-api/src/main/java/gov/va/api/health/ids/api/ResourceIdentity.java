package gov.va.api.health.ids.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ResourceIdentity {
  String identifier;
  String system;
  String resource;
}
