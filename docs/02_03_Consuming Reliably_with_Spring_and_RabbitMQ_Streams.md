# Single RabbitMQ Cluster

Start RabbitMQ with streams
```shell
./deployments/docker/rabbit/start-rabbit-w-streams.sh
```

------------------------------------------------
# Run Account Balance

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
  "amount": 30
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

Kill Account Balance


Make Payment

```shell
curl -X 'POST' \
  'http://localhost:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 30
}'
```

Run Account Balance

```shell
java -jar applications/account-balance-service/target/account-balance-service-0.0.1-SNAPSHOT.jar --spring.rabbitmq.username=app --spring.rabbitmq.password=app --spring.profiles.active=valKey
```

Get balance

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
./deployments/docker/valkey/start-val-key-no-persistence.sh
```

Get balance

```shell
curl -X 'GET' \
  'http://localhost:8080/readBalanceFunction/001' \
  -H 'accept: application/json'
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


