package gov.va.api.health.autoconfig.configuration.testapp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class Fugazi {
  String thing;
  Instant time;
  CustomBuilder cb;
  Specified specified;

  @Value
  @Builder
  @JsonDeserialize(builder = Specified.SpecifiedBuilder.class)
  public static class Specified {
    boolean troofs;
  }

  @Data
  @Builder(builderMethodName = "makeOne", builderClassName = "CustomBuilderMaker")
  @JsonDeserialize(builder = CustomBuilder.CustomBuilderMaker.class)
  public static class CustomBuilder {
    int one;

    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "")
    public static class CustomBuilderMaker {}
  }
}
