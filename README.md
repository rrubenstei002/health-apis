# health-apis

This project encapsulates the Health APIs. The system consists of three
[Spring Boot](https://spring.io/projects/spring-boot) microservices that provide
[Argonaut](http://www.fhir.org/guides/argonaut/r2/profiles.html) compliant resources
backed by information from the Corporate Data Warehouse.

###### Health API applications
![applications](src/plantuml/apps.png)

----

## Building
- [Java Development Kit](https://openjdk.java.net/) 8
- [Maven](http://maven.apache.org/) 3.5
- [PlantUml](http://plantuml.com/) for diagrams
- Recommended [IntelliJ](https://www.jetbrains.com/idea/)
  or [Eclipse](https://www.eclipse.org/downloads/packages/installer)
  with the following plugins
  - [Lombok](https://projectlombok.org/)
  - [Google Java Format](https://github.com/google/google-java-format)
- [git-secrets](https://github.com/awslabs/git-secrets)    

#### Maven
- Formats Java, XML, and JSON files
  (See the [Style Guide](https://google.github.io/styleguide/javaguide.html))
- Enforces unit test code coverage
- Performs [Checkstyle](http://checkstyle.sourceforge.net/) analysis using Google rules
- Performs [SpotBugs](https://spotbugs.github.io/) analysis
  with [Find Security Bugs](http://find-sec-bugs.github.io/) extensions
- Enforces Git branch naming conventions to support Jira integration


The above build steps can be skipped for use with IDE launch support by disabling the
_standard_ profile, e.g. `mvn -P'!standard' package`

###### Configuration
The spring application requires an [external configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html) which can be provided by a teammate.

#### git-secrets
git-secrets must be installed and configured to scan for AWS entries and the patterns in
[.git-secrets-patterns](.git-secrets-patterns).
The [init-git-secrets.sh](src/scripts/init-git-secrets.sh) can be used to simply set up.

> ###### ⚠ Mac users
> If using [Homebrew](https://brew.sh/), use `brew install --HEAD git-secrets` as decribed
> by [this post](https://github.com/awslabs/git-secrets/issues/65#issuecomment-416382565) to
> avoid issues committing multiple files.
