package gov.va.api.health.ids.service.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JacksonConfig {

  /**
   * Look for a deserialize annotation using the builder for immutable data types. Your classes
   * should look like this:
   *
   * <pre>
   * &#64;JsonDeserialize(builder = Foo.FooBuilder.class)
   * &#64;Value
   * &#64;Builder
   * public class Foo {
   *    String ack;
   *    String bar;
   * }
   * </pre>
   */
  @Bean
  ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    mapper.setAnnotationIntrospector(
        new JacksonAnnotationIntrospector() {
          @Override
          public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
            if (ac.hasAnnotation(JsonPOJOBuilder.class)) {
              return super.findPOJOBuilderConfig(ac);
            }
            return new JsonPOJOBuilder.Value("build", "");
          }
        });
    mapper.setVisibility(
        mapper
            .getSerializationConfig()
            .getDefaultVisibilityChecker()
            .withFieldVisibility(Visibility.ANY));
    return mapper;
  }
}
