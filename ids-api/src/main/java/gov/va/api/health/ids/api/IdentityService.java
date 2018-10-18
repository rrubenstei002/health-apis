package gov.va.api.health.ids.api;

import java.util.List;

public interface IdentityService {

  List<ResourceIdentity> lookup(String id);

  List<Registration> register(List<ResourceIdentity> identities);

  class IdentityServiceException extends RuntimeException {
    IdentityServiceException(String message) {
      super(message);
    }
  }

  class UnknownIdentity extends IdentityServiceException {
    public UnknownIdentity(String id) {
      super(id);
    }
  }

  class LookupFailed extends IdentityServiceException {
    public LookupFailed(String id, String reason) {
      super(id + " Reason: " + reason);
    }
  }

  class RegistrationFailed extends IdentityServiceException {
    public RegistrationFailed(String message) {
      super(message);
    }
  }
}
