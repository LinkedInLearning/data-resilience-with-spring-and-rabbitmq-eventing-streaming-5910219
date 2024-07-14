# Single RabbitMQ Cluster



```shell
mkdir -p $PWD/deployments/local/runtime/rabbitmq/persistence
docker run -v $PWD/deployments/local/runtime/rabbitmq/persistence:/bitnami/rabbitmq/mnesia -d --name rabbitmq-server -p 15672:15672 -p 5672:5672 -e RABBITMQ_USERNAME=app -e RABBITMQ_PASSWORD=app -e RABBITMQ_MANAGEMENT_ALLOW_WEB_ACCESS=true  bitnami/rabbitmq:latest
```


```shell
docker logs rabbitmq-server
```

Open Management


Login user: app password:app

```shell
open http://localhost:15672
```

------------------------------------------------
# Run Account Balance App

Build application
```shell
mvn clean package
```


```shell
java -jar applications/account-balance-service/target/account-balance-service-0.0.1-SNAPSHOT.jar --spring.rabbitmq.username=app --spring.rabbitmq.password=app
```

------------------------------------------------
## DEMO - Send Payment with RabbitMQ Console


Get balance

```shell
curl -X 'GET' \
  'http://localhost:8080/readBalanceFunction/001' \
  -H 'accept: application/json'
```

Open Console

```shell
open http://localhost:15672
```

Open RabbitMQ Console -> Exchanges ->  payment 

```json
{
  "id": "001",
  "amount": 30.00
}
```

Get balance

```shell
curl -X 'GET' \
  'http://localhost:8080/readBalanceFunction/001' \
  -H 'accept: application/json'
```


# Demo - Issue with In-Memory Repository

- Kill account-balance-service
- Restart account-balance-service

```shell
java -jar applications/account-balance-service/target/account-balance-service-0.0.1-SNAPSHOT.jar --spring.rabbitmq.username=app --spring.rabbitmq.password=app
```

Get balance

```shell
curl -X 'GET' \
  'http://localhost:8080/readBalanceFunction/001' \
  -H 'accept: application/json'
```