

# Single RabbitMQ Cluster

Setup 

```shell
rm -rf $PWD/deployments/local/runtime/rabbitmq/persistence
docker network rm valkey
```

```shell
mkdir -p $PWD/deployments/local/runtime/rabbitmq/persistence
docker run  --rm -v $PWD/deployments/local/runtime/rabbitmq/persistence:/bitnami/rabbitmq/mnesia --name rabbitmq-server -p 15672:15672 -p 5672:5672 -e RABBITMQ_USERNAME=app -e RABBITMQ_PASSWORD=app -e RABBITMQ_MANAGEMENT_ALLOW_WEB_ACCESS=true  bitnami/rabbitmq:latest
```

Open Management


Login user: app password:app

```shell
open http://localhost:15672
```

Everything empty

# ValKey


```shell
docker network create valkey
```

```shell
docker run --rm -e ALLOW_EMPTY_PASSWORD=yes --name valkey-server --network valkey -p 6379:6379   bitnami/valkey:7.2.5
```



------------------------------------------------
# Run Account Balance App


```shell
mvn clean package
java -jar applications/account-balance-service/target/account-balance-service-0.0.1-SNAPSHOT.jar --spring.rabbitmq.username=app --spring.rabbitmq.password=app --pring.rabbitmq.host=localhost --spring.rabbitmq.port=5672 --spring.data.redis.host=localhost --spring.data.redis.port=6379
```

Open Rabbitmq management

- Review connection
- Channel
- Exchange
- Queues


Get Balance

```shell
curl -X 'GET' \
  'http://localhost:8080/readBalanceFunction/001' \
  -H 'accept: application/json'
```


------------------------------------------------
## DEMO - Send Payment with RabbitMQ Console


Open Console

```shell
open http://localhost:15672
```

Open RabbitMQ Console -> Exchanges ->  payment 

```json
{
  "id": "001",
  "amount": 250.00
}
```

Get balance

```shell
curl -X 'GET' \
  'http://localhost:8080/readBalanceFunction/001' \
  -H 'accept: application/json'
```

Add another payment

```json
{
  "id": "001",
  "amount": 250.00
}
```

Get balance

```shell
curl -X 'GET' \
  'http://localhost:8080/readBalanceFunction/001' \
  -H 'accept: application/json'
```

END DEMO

-------------------------


Data saved in ValKey



Connect CLI
```shell
docker run -it --rm --network valkey bitnami/valkey:latest valkey-cli -h valkey-server
```

```shell
keys *
```

```shell
keys *
```

Looks value command
```shell
HGETALL  demo.data.resiliency.account.balance.domain.Balance:001
```

END DEMO
