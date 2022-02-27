- There were some issues with minio deployment using Helm as well as using Minio operator (https://docs.min.io/docs/deploy-minio-on-kubernetes.html)
- So, a simple kubernetes deployment is done instead using the *deployment.yaml* file (the *service.yaml* and *values.yaml* are currently unused).
- mc is the minio client, used to interact with minio through the CLI
- Before deploying the *adaptor server*, makes sure that the *flink* bucket and *recovery* bucket exist in minio (create
 from the Web UI if not existing, access using instructions in [Ingress Readme](../ingress/README.md)).