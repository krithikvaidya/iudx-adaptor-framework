- This folder contains the files that are part of the official Flink installation (https://flink.apache.org/downloads.html)
- Flink 1.14.0
- The *conf/flink-conf.yaml* file has been modified according to our needs
- To run a session cluster,
```
./bin/kubernetes-session.sh -Dkubernetes.cluster-id=flink-cluster -Dkubernetes.service-account=flink-service-account   -Dkubernetes.container.image=f114:latest 
```
- Running the above will automatically read *conf/flink-conf.yaml* and *~/.kube/config*
- To submit an example job,
```
./bin/flink run     --target kubernetes-session     -Dkubernetes.cluster-id=flink-cluster -Dkubernetes.service-account=flink-service-account     ./examples/streaming/TopSpeedWindowing.jar
```
- With ```kubectl get svc```, you can get the LoadBalancer IP of the cluster and access the Flink web dashboard.