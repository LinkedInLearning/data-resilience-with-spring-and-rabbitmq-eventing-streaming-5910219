# Enable Shovel and Federations

```shell
helm uninstall valkey 
k delete pvc valkey-data-valkey-master-0
``````


```shell
k delete -f deployments/kubernetes/dataServices/rabbit/rabbitmq-node3.yml
```

```shell
k apply -f deployments/kubernetes/dataServices/rabbit/rabbitmq-node3_multisite.yml
```

```shell
./deployments/kubernetes/dataServices/valKey/valKey.sh
```
```shell
kubectl get pods -w
```

Delete Apps to pickup new credentials

```shell
k delete pods  -l  name=account-balance-service
k delete pods  -l  name=payment-service
```


```shell
k get pods -w
```



# Create User

```shell
export RABBIT_HOST=`kubectl get services rabbitmq --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
open "http://$RABBIT_HOST:15672"
```

```shell
./deployments/kubernetes/dataServices/rabbit/scripts/get-credentials.sh
```

Click -> Admin -> Add User

User/Password

```
federation/changeme
```


- Tag: admin 
- Add User
- 

Add permission

- Click user -> federation
- Set permission on vhost /



# Site #2 


Open

https://console.cloud.google.com/kubernetes/list/overview?authuser=1&project=resiliency-427322

Console -> Clusters


resiliency-cluster-2

Connect to cluster 2

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
kubectl apply -f deployments/kubernetes/dataServices/rabbit/rabbitmq-node3_multisite.yml
```

-------

## ValKey


```shell
./deployments/kubernetes/dataServices/valKey/valKey.sh
```

```shell
kubectl get pods -w
```

-------

## Account Balance

```shell
kubectl apply -f deployments/kubernetes/apps/account-balance-service/account-balance-service.yml
```

## Setup Federation


```shell
export RABBIT_HOST=`kubectl get services rabbitmq --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
open "http://$RABBIT_HOST:15672"
```

```shell
./deployments/kubernetes/dataServices/rabbit/scripts/get-credentials.sh
```

Create Federation Upstream

- Click -> Admin
- Federation Upstream
- Add a new upstream

Federation Details

- name: payments_upstream_federations
- uri: amqp://federation:changeme@<RABBIT_CLUSTER1_SERVICE_HOST>:5672
- exchange: payment
- Add upstream


Create Policy

- Click Admin -> Policies
- name: payments_upstream_federations
- pattern: payment
- Apply to : Exchange
- federation-upstream-set: all
- Add/ update policy


Review connection in cluster 1


----------------

# Payment replication test

```shell
export ACCT_CLUSTER2_HOST=`kubectl get services account-balance-service --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
echo $ACCT_CLUSTER2_HOST
```


Get Balance Site 2

```shell
curl -X 'GET' "http://$ACCT_CLUSTER2_HOST:8080/readBalanceFunction/001" -H 'accept: application/json'
```


Connect Site 1


```shell
export PAYMENT_CLUSTER1_HOST=`kubectl get services payment-service --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
echo $PAYMENT_CLUSTER1_HOST
```

```shell
export ACCT_CLUSTER1_HOST=`kubectl get services account-balance-service --output jsonpath='{.status.loadBalancer.ingress[0].ip}'`
echo $ACCT_CLUSTER1_HOST
```


```shell
curl -X 'GET' "http://$ACCT_CLUSTER1_HOST:8080/readBalanceFunction/001" -H 'accept: application/json'
```

```shell
curl -X 'POST' \
  "http://$PAYMENT_CLUSTER1_HOST:8081/functions/makePaymentConsumer" \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "001",
  "amount": 100
}'
```


```shell
curl -X 'GET' "http://$ACCT_CLUSTER1_HOST:8080/readBalanceFunction/001" -H 'accept: application/json'
```

```shell
curl -X 'GET' "http://$ACCT_CLUSTER2_HOST:8080/readBalanceFunction/001" -H 'accept: application/json'
```
-------------------

