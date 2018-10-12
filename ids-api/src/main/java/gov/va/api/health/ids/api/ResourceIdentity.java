package gov.va.api.health.ids.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = ResourceIdentity.ResourceIdentityBuilder.class)
@Value
@Builder(toBuilder = true)
public class ResourceIdentity {
  String identifier;
  String system;
  String resource;
}
