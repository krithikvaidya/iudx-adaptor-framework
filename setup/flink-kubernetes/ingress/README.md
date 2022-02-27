## Ingress resource definitions

- *ingress.yaml* specifies rules to direct all ingress traffic at the NLB (http://a25464a7462564b66b42a86c78297847-bbaf404abb3e175a.elb.ap-south-1.amazonaws.com/)
to the Flink Web UI.
- *monitoring-ingress.yaml* is currently unused.
- *minio*, *mockserver* and *grafana* are exposed using NodePort services.
  - *minio* and *mockserver* services are in the *default* namespace (exposed at ports 30005 and 30001 respectively). 
    To access them from outside, get the IP address of any of the kubernetes nodes (using `kubectl get nodes -o wide`) and 
    visit (ip_address:port).
  - *grafana* service is in the *monitoring* namespace and is exposed at port 32000.