package gov.va.api.health.ids.service.controller;

import gov.va.api.health.ids.api.ResourceIdentity;
import gov.va.api.health.ids.service.controller.impl.ResourceIdentityDetail;
import gov.va.api.health.ids.service.controller.impl.ResourceIdentityDetailRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
@Validated
@RequestMapping("/api")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@Slf4j
public class IdServiceV1ApiController {

  private final ResourceIdentityDetailRepository repository;

  /** Implementation of /v1/ids/{publicId}. See api-v1.yaml. */
  @RequestMapping(
    value = "/v1/ids/{publicId}",
    produces = {
      "application/json",
    },
    method = RequestMethod.GET
  )
  @SneakyThrows
  public ResponseEntity<List<ResourceIdentity>> lookup(
      @Valid @PathVariable("publicId") @Pattern(regexp = "[-A-Za-z0-9]+") String publicId,
      ServerWebExchange exchange) {

    List<ResourceIdentity> identities =
        repository
            .findByUuid(publicId)
            .stream()
            .map(ResourceIdentityDetail::asResourceIdentity)
            .collect(Collectors.toList());

    log.info("Found {}", identities);

    return ResponseEntity.ok().body(identities);
  }
}
