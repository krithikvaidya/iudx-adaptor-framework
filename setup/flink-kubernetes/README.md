- The description of each component is in the respective subfolder's README
### Ingress
- nginx ingress controller was set up, and two ingress resource files are defined (*ingress.yaml* and *monitoring-ingress.yaml*)
- 
- For minio, 


- The /etc/hosts files also needs to be modified as below, to add these additional DNS entries for local testing (refer to: https://kubernetes.io/docs/tasks/access-application-cluster/ingress-minikube/)
```
<ingress-controller-address> flink-deployment
<ingress-controller-address> adaptor-server
<ingress-controller-address> grafana
```