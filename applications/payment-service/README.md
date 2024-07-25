## Builder Docker


docker login
```shell
mvn install
cd applications/payment-service
mvn spring-boot:build-image
docker tag payment-service:0.0.1-SNAPSHOT cloudnativedata/payment-service:0.0.1-SNAPSHOT 
docker push cloudnativedata/payment-service:0.0.1-SNAPSHOT
```