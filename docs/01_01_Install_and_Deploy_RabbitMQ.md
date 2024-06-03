# Single Cluster

```shell
mkdir -p $PWD/deployments/local/runtime/rabbitmq/persistence
docker run -v $PWD/deployments/local/runtime/rabbitmq/persistence:/bitnami/rabbitmq/mnesia -d --name rabbitmq-server -p 15672:15672 -e RABBITMQ_USERNAME=guest -e RABBITMQ_PASSWORD=guest -e RABBITMQ_MANAGEMENT_ALLOW_WEB_ACCESS=true  bitnami/rabbitmq:latest
```


```shell
docker logs rabbitmq-server
```

Open Management


Login user: guest password:guest

```shell
open http://localhost:15672
```
