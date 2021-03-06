package gov.va.api.health.autoconfig.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Look for a deserialize annotation using the builder for immutable data types. This configuration
 * supports
 *
 * <ul>
 *   <li>Property level access
 *   <li>JDK 8 data type support, e.g. Optional
 *   <li>Java time support, e.g. Instant
 *   <li>Fails on unknown properties
 *   <li>Lombok &#64;Value &#64;Builder with out needing to specify Jackson annotations
 * </ul>
 *
 * <p>Your classes should look like one of these:
 *
 * <p>No additional Jackson annotations needed!
 *
 * <pre>
 * &#64;Value
 * &#64;Builder
 * &#64;JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
 * public class Easy {
 *    String ack;
 *    String bar;
 * }
 * </pre>
 *
 * Alternatively, you can specify the deserializer to be the builder.
 *
 * <pre>
 * &#64;JsonDeserialize(builder = Foo.FooBuilder.class)
 * &#64;Value
 * &#64;Builder
 * &#64;JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
 * public class Foo {
 *    String ack;
 *    String bar;
 * }
 * </pre>
 */
@Configuration
@Slf4j
public class JacksonConfig {

  /**
   * Return a ready to use mapper that will work with classes adhering to the conventions described
   * in the class-level documentation.
   */
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper =
        new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule())
            .setAnnotationIntrospector(new LombokAnnotationIntrospector())
            .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .enable(MapperFeature.AUTO_DETECT_FIELDS)
            .setVisibility(PropertyAccessor.ALL, Visibility.ANY);
    return mapper;
  }

  /**
   * The lombok class annotation inspector provides support for this project's style of builders.
   * This allows @Value classes with @Builders to be automatically supported for deserialization. It
   * will look for a builder class using the standard Lombok naming conventions and assume builder
   * methods do not have a prefix, e.g. "property" instead of "setProperty" or "withProperty".
   * However, you can still use @JsonPOJOBuilder if you need to override this inspectors default
   * behavior.
   */
  private static class LombokAnnotationIntrospector extends JacksonAnnotationIntrospector {

    @Override
    public Class<?> findPOJOBuilder(AnnotatedClass ac) {
      /*
       * Attempt to allow the default mechanism work. However, if no builder is found using Jackson
       * annotations, try to find a lombok style builder.
       */
      Class<?> pojoBuilder = super.findPOJOBuilder(ac);
      if (pojoBuilder != null) {
        return pojoBuilder;
      }
      String className = ac.getAnnotated().getSimpleName();
      String lombokBuilder = ac.getAnnotated().getName() + "$" + className + "Builder";
      try {
        return Class.forName(lombokBuilder);
      } catch (ClassNotFoundException e) {
        /* Default lombok builder does not exist. */
      }
      return null;
    }

    @Override
    public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
      if (ac.hasAnnotation(JsonPOJOBuilder.class)) {
        return super.findPOJOBuilderConfig(ac);
      }
      return new JsonPOJOBuilder.Value("build", "");
    }
  }
}
