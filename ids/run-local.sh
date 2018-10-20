#!/usr/bin/env bash

echo "Running ID service with embedded H2 database"

cd $(dirname $0)
java \
  -cp target/ids-1.0.9-SNAPSHOT.jar:target/h2.jar \
  -Dloader=gov.va.api.health.ids.service.Application \
  -Dspring.jpa.generate-ddl=true \
  -Dspring.jpa.hibernate.ddl-auto=create-drop \
  -Dspring.datasource.driver-class-name=org.h2.Driver \
  -Dspring.datasource.url=jdbc:h2:mem:whatever  \
  -Dspring.datasource.username=whatever \
  -Dspring.datasource.password=whatever \
  -Dserver.ssl.key-store=file:target/certs/system/DVP-DVP-NONPROD.jks \
  -Dserver.ssl.key-store-password='L!b3rty!T$Fr33d0m!' \
  -Dserver.ssl.key-alias=internal-sys-dev \
  -Dssl.key-store=file:target/certs/system/DVP-DVP-NONPROD.jks \
  -Dssl.key-store-password='L!b3rty!T$Fr33d0m!' \
  -Dssl.client-key-password='L!b3rty!T$Fr33d0m!' \
  -Dssl.use-trust-store=false \
  -Dssl.trust-store=file:target/certs/system/DVP-NONPROD-truststore.jks \
  -Dssl.trust-store-password='L!b3rty!T$Fr33d0m!' \
  org.springframework.boot.loader.PropertiesLauncher
