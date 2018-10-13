package gov.va.api.health.ids.api;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ResourceIdentity {
  String identifier;
  String system;
  String resource;
}
