# microservices

# microservices part - 1
goal v1.0: achieve a basic microservice implementation in spring boot application

catalogapi - spring boot application
  what is it?
   - a simple spring boot application 
   - exposes few rest urls for CRUD operations
      - create / update (/catalog/products)
      - read (/catalog/products and /catalog/products/{id})
      - delete(/catalog/products/{id})
   - server port 5000
   - uses dynamo db (connecting to aws)
   - uses maven (build automation tool)
   
   
   can i run this locally?
   - yes
   
   what do i need to run locally?
   - eclipse latest
   - java se 8
   
   technology stack learnt
   - spring boot and REST (files: java libraries)
   - maven (files: pom.xml)
   - aws dynamo db (nosql database table creation, updation)
   
   http url to access rest services
   - http://localhost:5000/catalog/products
   
# microservices part - 2
goal v2.0: achieve a basic microservice implementation in spring boot application 
  - more details of this, is in goal v1.0
goal v2.1: integrate with spring boot actuator and micrometer to send data to prometheus
  technology stack learnt
  - docker basics
  - prometheus
  - grafana
  
  steps to start prometheus and grafana in docker container
  - install docker locally
  - docker pull prom/prometheus (pulls docker image)
  - docker run -d --name=prometheus -p 9090:9090 -v <path to prometheus.yml>:/etc/prometheus/prometheus.yml prom/prometheus —config.file=/etc/prometheus/prometheus.yml
  
  
  

 
  notes:
     yesterday, i read this in some blog (a microservice must support visibility and transparency, indicators of its own state  and of system state, in a single plan of glass experience. The dropwizard metrics library is one of the more popular approaches to capturing application metrics (gauges, meters, histograms, and counters). Spring boot's actuator module provides deep integration with the DropWizard Metrics library, and supports exposing health end points, environment information, endpoint mapping information, request logs, and more. Time-series databases like Hazelcast, StatsD, Graphite, InfluxDB, and OpenTSDB support the visualization and processing of metrics. DropWizard metrics and SpringBoot actuator can transparently export collected metrics to these time-series databases.)

  extras of catalogapi
  - in pom.xml, in dependencies, 2 entries below are used to send spring boot application data to prometheus and 
    - io.micrometer, org.springframework.boot
    
   
