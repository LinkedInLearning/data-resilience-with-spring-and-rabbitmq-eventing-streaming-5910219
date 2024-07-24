
# Start Minikube

Start Minikube

```shell
minikube start
```

Start Tunnel
```shell
minikube tunnel
```

# Install RabbitMQ

Install RabbitMQ Cluster Operator

```shell
kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"
```

Get Pods

```shell
kubectl get pods -n rabbitmq-system
```




```shell
kubectl create namespace resilency
```

```shell
kubectl config set-context --current --namespace=resilency
```

Install RabbitMQ

```shell
kubectl apply -f deployments/kubernetes/dataServices/rabbit/rabbitmq-node1.yml
```


```shell
kubectl get pods -w
```

```shell
kubectl get services
```


```shell
open http://127.0.0.1:15672
```


```shell
./deployments/kubernetes/dataServices/rabbit/scripts/get-credentials.sh
```


```shell
kubectl get statefulsets
```

```shell
kubectl get pods
```

SHOW DIAGRAM

```shell
kubectl delete pod rabbitmq-server-0
```

```shell
open http://127.0.0.1:15672
```

```shell
kubectl get pods -w
```

Delete RabbitMQ

```shell
kubectl delete -f deployments/kubernetes/dataServices/rabbit/rabbitmq-node1.yml
```


------------

# RabbitMQ Cluster


SHOW DIAGRAM

```shell
kubectl apply -f deployments/kubernetes/dataServices/rabbit/rabbitmq-node3.yml
```



```shell
kubectl get pods -w
```

```shell
./deployments/kubernetes/dataServices/rabbit/scripts/get-credentials.sh
```



```shell
open http://127.0.0.1:15672
```


```shell
kubectl get pods
```


```shell
kubectl delete pod rabbitmq-server-0
```

```shell
open http://127.0.0.1:15672
```

```shell
kubectl get pods -w
```
