package gov.va.api.health.ids.service.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import gov.va.api.health.ids.api.ResourceIdentity;
import gov.va.api.health.ids.service.controller.impl.ResourceIdentityDetail;
import gov.va.api.health.ids.service.controller.impl.ResourceIdentityDetailRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;

public class IdServiceV1ApiControllerTest {

  @Mock ResourceIdentityDetailRepository repo;
  @Mock ServerWebExchange exchange;

  IdServiceV1ApiController controller;

  @Before
  public void _init() {
    MockitoAnnotations.initMocks(this);
    controller = new IdServiceV1ApiController(repo);
  }

  private ResourceIdentity resourceIdentity(int i) {
    return ResourceIdentity.builder().identifier("i" + i).resource("r" + i).system("s" + i).build();
  }

  private ResourceIdentityDetail detail(String uuid, int i) {
    return ResourceIdentityDetail.builder()
        .id(i)
        .identifier("i" + i)
        .resource("r" + i)
        .system("s" + i)
        .stationIdentifier("st" + i)
        .uuid(uuid)
        .build();
  }

  @Test
  public void lookupReturnsIdentitiesWhenFound() {

    List<ResourceIdentityDetail> searchResults =
        Arrays.asList(detail("x", 3), detail("x", 2), detail("x", 1));
    when(repo.findByUuid("x")).thenReturn(searchResults);

    ResponseEntity<List<ResourceIdentity>> actual = controller.lookup("x", exchange);

    assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(actual.getBody())
        .containsExactlyInAnyOrder(resourceIdentity(3), resourceIdentity(2), resourceIdentity(1));
  }
}
