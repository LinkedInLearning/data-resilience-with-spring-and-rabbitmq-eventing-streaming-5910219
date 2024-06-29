


[GKE](https://console.cloud.google.com/projectselector2/kubernetes/list/overview?referrer=search&organizationId=987448017264&supportedpurview=project)

Create Project

Project name: resiliency

Enable Kubernetes Engine API


Click cluster -> create 

cluster name: resiliency-cluster-1

Click cluster -> connect


Install gcloud

```shell
https://cloud.google.com/sdk/docs/install-sdk
```


```shell
gcloud auth login
```

Inst auth-plugin

```shell
https://cloud.google.com/kubernetes-engine/docs/how-to/cluster-access-for-kubectl#install_plugin
```


```shell
gcloud components install gke-gcloud-auth-plugin
```

Connect

--------------------------------------------
# Deployment Rabbit


Install RabbitMQ Cluster Operator

```shell
kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"
```

Get Pods

```shell
kubectl get pods -n rabbitmq-system -w
```




```shell
kubectl create namespace resilency
```


```shell
kubectl config set-context --current --namespace=resilency
```

Install RabbitMQ

```shell
kubectl apply -f deployments/kubernetes/dataServices/rabbit/rabbitmq-node3.yml
```


```shell
kubectl get pods -w
```

-------

## ValKey


```shell
./deployments/kubernetes/dataServices/valKey/valKey.sh
```

-------

## Account Balance

```shell
kubectl apply -f deployments/kubernetes/apps/account-balance-service/account-balance-service-streams.yml
```


```shell
kubectl apply -f deployments/kubernetes/apps/payment-service/payment-service.yml
```

-------


```shell
echo RABBIT_HOST=`kubectl get services rabbitmq --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
```

```shell
./deployments/kubernetes/dataServices/rabbit/scripts/get-credentials.sh
```


```shell
export PAYMENT_HOST=`kubectl get services payment-service --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
echo $PAYMENT_HOST
```

```shell
curl -X 'POST' \
  "http://$PAYMENT_HOST:8081/functions/makePaymentConsumer" \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 100
}'
```

```shell
export ACCT_HOST=`kubectl get services account-balance-service --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
echo $ACCT_HOST
```



```shell
curl -X 'GET' "http://$ACCT_HOST:8080/readBalanceFunction/001" -H 'accept: application/json'
```


```shell
k delete pod valkey-master-0
```


```shell
curl -X 'POST' \
  "http://$PAYMENT_HOST:8081/functions/makePaymentConsumer" \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 100
}'
```

```shell
curl -X 'GET' "http://$ACCT_HOST:8080/readBalanceFunction/001" -H 'accept: application/json'
```



```shell
helm uninstall valkey 
```

```shell
curl -X 'POST' \
  "http://$PAYMENT_HOST:8081/functions/makePaymentConsumer" \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 100
}'
```

```shell
curl -X 'GET' "http://$ACCT_HOST:8080/readBalanceFunction/001" -H 'accept: application/json'
```


```shell
./deployments/kubernetes/dataServices/valKey/valKey.sh
```


```shell
k delete pods  -l  name=account-balance-service
```

```shell
curl -X 'GET' "http://$ACCT_HOST:8080/readBalanceFunction/001" -H 'accept: application/json'
```
