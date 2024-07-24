
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
kubectl create namespace resiliency
```

```shell
kubectl config set-context --current --namespace=resiliency
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

USER: default_user__s14uWKXPz614-ajLJb
PASWORD: RAZZdnDuGf719TbdEZkoKbK3GNyPlhFP


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


Install k9

```shell
brew install derailed/k9s/k9s
```


```shell
kubectl delete pod rabbitmq-server-0
```

```shell
open http://127.0.0.1:15672
```

Show pods in k9s

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


Show PODS in k9s

```shell
./deployments/kubernetes/dataServices/rabbit/scripts/get-credentials.sh
```


USER: default_user_4DqcHO9GN0qKhq5DGh0
PASWORD: vsjxPWD3VAXzz95lWd1ZneEZeIv40lO0


```shell
open http://127.0.0.1:15672
```


```shell
kubectl get pods
```


```shell
kubectl delete pod rabbitmq-server-0
kubectl delete pod rabbitmq-server-1
kubectl delete pod rabbitmq-server-2
```

```shell
open http://127.0.0.1:15672
```

```shell
kubectl get pods -w
```
