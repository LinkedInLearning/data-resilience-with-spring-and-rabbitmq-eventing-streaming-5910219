
# Pre-requisite


```shell
  curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
  kubectl delete -f deployments/kubernetes/apps/payment-service/payment-service.yml
  kubectl delete -f deployments/kubernetes/apps/account-balance-service/account-balance-service.yml
  kubectl get pods
  kubectl delete RabbitMQCluster rabbitmq
  helm uninstall valkey
  kubectl delete pvc valkey-data-valkey-master-0  valkey-data-valkey-replicas-0  valkey-data-valkey-replicas-1 valkey-data-valkey-replicas-2
```

- Install/Start Minikube 
- Install RabbitMQ Cluster Operator


Start Tunnel
```shell
minikube tunnel
```

# RabbitMQ

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

------------

# Install ValKey


Install Helm

```shell
helm install valkey --set replica.replicaCount=0 oci://registry-1.docker.io/bitnamicharts/valkey
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
open http://127.0.0.1:15672
```

```shell
kubectl get pods -w
```

# Deploy Account Balance App


```shell
mvn install
cd applications/account-balance-service
mvn spring-boot:build-image

docker images

docker login
docker tag account-balance-service:0.0.1-SNAPSHOT cloudnativedata/account-balance-service:0.0.1-SNAPSHOT 

docker push cloudnativedata/account-balance-service:0.0.1-SNAPSHOT
```


```shell
kubectl apply -f deployments/kubernetes/apps/account-balance-service/account-balance-service.yml
```

```shell
kubectl get pods -w
```



# Deploy Payment App


```shell
cd applications/payment-service
mvn spring-boot:build-image
docker login
docker tag payment-service:0.0.1-SNAPSHOT cloudnativedata/payment-service:0.0.1-SNAPSHOT 
docker push cloudnativedata/payment-service:0.0.1-SNAPSHOT
```

cd ../..
```shell
kubectl apply -f deployments/kubernetes/apps/payment-service/payment-service.yml
```

```shell
kubectl get pods -w
```


```shell
kubectl get services
```


Read Balance


```shell
curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
```


```shell
curl -X 'POST' \
  'http://127.0.0.1:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 1000
}'
```

```shell
curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
```


```shell
k get pods
```

```shell
k delete pod -l name=account-balance-service
curl -X 'POST' \
  'http://127.0.0.1:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 1000
}'
```


```shell
curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
```

```shell
k get pods -w
```

```shell
kubectl delete pod -l name=payment-service
```

```shell
curl -X 'POST' \
  'http://127.0.0.1:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 100
}'
```

```shell
curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
```


```shell
kubectl  delete pod rabbitmq-server-0
```

```shell
curl -X 'POST' \
  'http://127.0.0.1:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 1000
}'
```

```shell
curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
```

---------------
## Scaling up RabbitMQ

```shell
kubectl delete -f deployments/kubernetes/dataServices/rabbit/rabbitmq-node1.yml
```


```shell
kubectl apply -f deployments/kubernetes/dataServices/rabbit/rabbitmq-node3.yml
```


Redeployment payments

```shell
kubectl delete -f deployments/kubernetes/apps/payment-service/payment-service.yml
```

```shell
kubectl apply -f deployments/kubernetes/apps/payment-service/payment-service.yml
```

```shell
k delete -f deployments/kubernetes/apps/account-balance-service/account-balance-service.yml
```


```shell
k apply -f deployments/kubernetes/apps/account-balance-service/account-balance-service.yml
```

```shell
curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
```


```shell
curl -X 'POST' \
  'http://127.0.0.1:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 100
}'
```

```shell
curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
```

```shell
./deployments/kubernetes/dataServices/rabbit/scripts/get-credentials.sh
```

View Connections/kill Payment connected broker
```shell
open http://localhost:15672
```



```shell
k delete pod rabbitmq-server-2
```

```shell
curl -X 'POST' \
  'http://127.0.0.1:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 100
}'
```

```shell
curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
```

View Connections/kill account balance connected broker
```shell
open http://localhost:15672
```

```shell
k delete pod rabbitmq-server-0
```

```shell
curl -X 'POST' \
  'http://127.0.0.1:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 100
}'
```


```shell
curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
```

```shell
k delete pod valkey-master-0
```

```shell
curl -X 'POST' \
  'http://127.0.0.1:8081/functions/makePaymentConsumer' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 100
}'
```


```shell
curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
```

---------------

```shell
export PAYMENT_SERVICE_HOST=`kubectl get services payment-service --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
```


```shell
CLIENT LIST
```


Connect CLI
```shell


echo VALKEY_PASSWORD=`kubectl get secrets/valkey -o jsonpath='{.data.*}' | base64 -d`

kubectl exec -it valkey-master-0 -- valkey-cli
```

```shell
auth your_current_valkey_password
```