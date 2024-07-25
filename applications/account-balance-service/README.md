## Builder Docker
Actuator

```shell
curl http://localhost:8080/actuator/health
```

```shell
#mvn install
cd applications/account-balance-service
mvn package
mvn spring-boot:build-image
docker tag account-balance-service:0.0.1-SNAPSHOT cloudnativedata/account-balance-service:0.0.1-SNAPSHOT 
docker push cloudnativedata/account-balance-service:0.0.1-SNAPSHOT
```