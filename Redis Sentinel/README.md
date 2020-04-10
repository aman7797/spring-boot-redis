**Pros**
- Deployment that consist of multiple nodes where one is elected as master and the rest are slaves. It adds high availability since in case of master failure one of the slaves will be automatically promoted to master. It is not scalable since the master node is the only node that can write data. You can configure the clients to direct read requests to the slaves, which will take some of the load from the master. However, in this case slaves might return stale data since they replicate the master asynchronously.
- Hardware - You can setup fully working Sentinel deployment with three nodes. Redis Cluster requires at least six nodes.
- Simplicity - usually it is easier to maintain and configure.

**Cons**
- Sentinel manages the failover, it doesn't configure Redis for HA
- The exception is that adding slaves can improve your read performance (in case you direct read requests to the slaves).