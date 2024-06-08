## Builder Docker

```shell
mvn install
cd applications/account-balance-service
mvn spring-boot:build-image
docker tag account-balance-service:0.0.1-SNAPSHOT cloudnativedata/account-balance-service:0.0.1-SNAPSHOT 
docker push cloudnativedata/account-balance-service:0.0.1-SNAPSHOT
```