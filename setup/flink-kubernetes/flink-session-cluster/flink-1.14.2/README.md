- This folder contains the flink-conf.yaml to be placed in subfolder *conf/* of the Flink distribution.
- Flink 1.14.2
- To run a session cluster, navigate to the Flink distribution folder, and run
```
./bin/kubernetes-session.sh -Dkubernetes.cluster-id=flink-cluster -Dkubernetes.service-account=flink-service-account   -Dkubernetes.container.image=f114:latest 
```
- Running the above will automatically read *conf/flink-conf.yaml* and *~/.kube/config*
- To submit an example job,
```
./bin/flink run     --target kubernetes-session     -Dkubernetes.cluster-id=flink-cluster -Dkubernetes.service-account=flink-service-account     ./examples/streaming/TopSpeedWindowing.jar
```
- With ```kubectl get svc```, you can get the LoadBalancer IP of the cluster and access the Flink web dashboard.