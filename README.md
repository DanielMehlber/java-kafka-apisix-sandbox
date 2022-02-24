# Components and Systems
## Apache APISIX
Apache APISIX is an **API Gateway** which is able to handle authorization, load 
balancing, logging and more.

It can be controlled/configured using REST-APIs and JSON.

### etcd nodes
> etcd is a strongly consistent, distributed key-value store that provides 
a reliable way to store data that needs to be accessed by a distributed 
system or cluster of machines

It is controllable with REST-APIs and provides a hierarchical key-value store.
It is used by Apache Kafka, Apache APISIX and many more.

### Prometheus
Monitoring solution

### Grafana
Open-Source Application for visualisation of data from many sources 
(in this case prometheus).

## Apache Kafka
Publish and Subscribe server.

### Kafka Zookeeper
> Zookeeper is a top-level software developed by Apache that acts as a 
> centralized service and is used to maintain naming and configuration 
> data and to provide flexible and robust synchronization within 
> distributed systems. Zookeeper keeps track of status of the Kafka 
> cluster nodes and it also keeps track of Kafka topics, partitions etc.