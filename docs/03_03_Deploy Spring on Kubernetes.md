
# Pre-requisite

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
./deployments/kubernetes/dataServices/valKey/helm-install.sh
```


```shell
helm install valkey oci://registry-1.docker.io/bitnamicharts/valkey
```

```shell
./deployments/kubernetes/dataServices/valKey/valKey.sh
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

# Deploy Account Balance App


```shell
mvn install
cd applications/account-balance-service
mvn spring-boot:build-image
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


Connect CLI
```shell


echo VALKEY_PASSWORD=`kubectl get secrets/valkey -o jsonpath='{.data.*}' | base64 -d`

kubectl exec -it valkey-master-0 -- valkey-cli
```

```shell
auth your_current_valkey_password
```

# Deploy Payment App


```shell
cd applications/payment-service
mvn spring-boot:build-image
docker login
docker tag payment-service:0.0.1-SNAPSHOT cloudnativedata/payment-service:0.0.1-SNAPSHOT 
docker push cloudnativedata/payment-service:0.0.1-SNAPSHOT
```


```shell
kubectl apply -f deployments/kubernetes/apps/payment-service/payment-service.yml
```

```shell
kubectl get pods -w
```


```shell
kubectl get services
```

```shell
export PAYMENT_SERVICE_HOST=`kubectl get services payment-service --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
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


Read Balance


```shell
curl -X 'GET' 'http://127.0.0.1:8080/readBalanceFunction/001' -H 'accept: application/json'
```

---------------



```shell
CLIENT LIST
```