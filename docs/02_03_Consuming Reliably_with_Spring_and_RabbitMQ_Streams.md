# Single RabbitMQ Cluster

Setup

```shell
rm -rf $PWD/deployments/local/runtime/rabbitmq/persistence
```

----

Start RabbitMQ with streams
```shell
docker run --rm -v $PWD/deployments/local/runtime/rabbitmq/persistence:/bitnami/rabbitmq/mnesia  --name rabbitmq-server --rm  -p 5552:5552  -p 15672:15672 -p 5672:5672 -e RABBITMQ_USERNAME=app  -e RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS='-rabbitmq_stream advertised_host localhost' -e RABBITMQ_PASSWORD=app -e RABBITMQ_MANAGEMENT_ALLOW_WEB_ACCESS=true  bitnami/rabbitmq:latest
```


```shell
docker exec rabbitmq-server rabbitmq-plugins enable rabbitmq_stream
docker exec rabbitmq-server rabbitmq-plugins enable rabbitmq_management
docker exec rabbitmq-server rabbitmqctl enable_feature_flag all
```

Start ValKey

```shell
docker run --rm -e ALLOW_EMPTY_PASSWORD=yes --name valkey-server --network valkey -p 6379:6379   bitnami/valkey:7.2.5

```

------------------------------------------------
# Run Account Balance

```shell
mvn clean package
```

```shell
java -jar applications/account-balance-service/target/account-balance-service-0.0.1-SNAPSHOT.jar --spring.rabbitmq.username=app --spring.rabbitmq.password=app --spring.profiles.active=valKey,stream 
```

# Run Payment App

```shell
java -jar applications/payment-service/target/payment-service-0.0.1-SNAPSHOT.jar --spring.rabbitmq.username=app --spring.rabbitmq.password=app
```

------------------------------------------------
## DEMO - Make Payment with Publisher App 


Make Payment

```shell
curl -X 'POST' \
  'http://localhost:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 500
}'
```

Open Console

```shell
open http://localhost:15672
```


Get balance

```shell
curl -X 'GET' \
  'http://localhost:8080/readBalanceFunction/001' \
  -H 'accept: application/json'
```

Make Payment

```shell
curl -X 'POST' \
  'http://localhost:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 1000
}'
```

```shell
curl -X 'POST' \
  'http://localhost:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 1000
}'
```
```shell
curl -X 'POST' \
  'http://localhost:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 1000
}'
```

```shell
curl -X 'GET' \
  'http://localhost:8080/readBalanceFunction/001' \
  -H 'accept: application/json'
```


Kill ValKey

```shell
docker rm -f valkey-server
```

Restart ValKey (without persistence)

```shell
docker run --rm -e ALLOW_EMPTY_PASSWORD=yes --name valkey-server --network valkey -p 6379:6379   bitnami/valkey:7.2.5
```

Restart Account Balance

```shell
java -jar applications/account-balance-service/target/account-balance-service-0.0.1-SNAPSHOT.jar --spring.rabbitmq.username=app --spring.rabbitmq.password=app --spring.profiles.active=valKey,stream 
```

Get balance

```shell
curl -X 'GET' \
  'http://localhost:8080/readBalanceFunction/001' \
  -H 'accept: application/json'
```

