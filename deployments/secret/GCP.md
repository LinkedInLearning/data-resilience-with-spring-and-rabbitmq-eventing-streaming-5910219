Check-Out Date - 06/04/24
Planned Check-In - 10/22/24
Course ID - 5910219
Instructor - Gregory Green        
Content Producer- Dione Johnson
Start Date - 07/22/24                 
Login - instructor.gcp05@trainingxyz.com
Password - (u8v@(MJ


http://34.23.237.10:15672/#/users/federation

amqp://federation:changeme@34.23.237.10:5672
---

# Site 1

```shell
gcloud container clusters get-credentials resiliency-cluster-1 --region us-east1 --project resiliency-427322
```

```shell
kubectl config set-context --current --namespace=resilency
```

# Site 2 

```shell
gcloud container clusters get-credentials resiliency-cluster-2 --region us-south1 --project resiliency-427322
```

```shell
kubectl config set-context --current --namespace=resilency
```