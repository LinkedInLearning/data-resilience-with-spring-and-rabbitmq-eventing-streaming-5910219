# Payment Service


This application publishes payment details to RabbitMQ.
It uses Spring Cloud Functions and Spring Cloud Stream.


## Builder Docker


```shell
mvn install
cd applications/account-balance-service
mvn spring-boot:build-image
docker tag account-balance-service:0.0.1-SNAPSHOT cloudnativedata/account-balance-service:0.0.1-SNAPSHOT 
```

You must login to docker hub with your credentials.

```shell
docker login
```
Push to docker hub (replace YOUR-ACCOUNT-NAME> with your login name. 

```shell
docker push <YOUR-ACCOUNT-NAME>/account-balance-service:0.0.1-SNAPSHOT
```

Example

```shell
docker push cloudnativedata/account-balance-service:0.0.1-SNAPSHOT
```