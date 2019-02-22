
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
- goal v2.0
  - achieve a basic microservice implementation in spring boot application 
      more details of this, is in goal v1.0
- goal v2.1
  - integrate with spring boot actuator and micrometer to send data to prometheus and use grafana to generate graphs.
  - why? 
    - Yesterday, i read this in some blog (a microservice must support visibility and transparency, indicators of its own state  and of system state, in a single plan of glass experience. 
    - The dropwizard metrics library is one of the more popular approaches to capturing application metrics (gauges, meters, histograms, and counters). 
    - Spring boot's actuator module provides deep integration with the DropWizard Metrics library, and supports exposing health end points, environment information, endpoint mapping information, request logs, and more. 
    - Time-series databases like Hazelcast, StatsD, Graphite, InfluxDB, and OpenTSDB support the visualization and processing of metrics. DropWizard metrics and SpringBoot actuator can transparently export collected metrics to these time-series databases.)

technology stack learnt
  - docker basics
  - prometheus
  - grafana
  - spring actuator and micrometer
  
  
  steps to start prometheus and grafana in docker container
  -> step 1: install docker locally
  -> step 2: run prometheus locally
    - docker pull prom/prometheus (pulls docker image)
    - docker run -d --name=prometheus -p 9090:9090 -v <path to prometheus.yml>:/etc/prometheus/prometheus.yml prom/prometheus —config.file=/etc/prometheus/prometheus.yml
    - (note: sample prometheus yml is committed in catalogapi project)
      (runs docker image on port 9090)
    - docker container ls (lists docker containers, here each container has id, which can be used to stop and remove from container before you run it again)
    - docker stop <id> (stop docker container)
    - docker rm <id> (remove docker container)
    -> step 3: run grafana locally (on port 3000)
      docker run -d --name=grafana -p 3000:3000 grafana/grafana

  check http urls now,
  - http://localhost:5000/actuator (spring boot actuator)
  - http://localhost:5000/actuator/prometheus (sprint boot micrometer supports prometheus to sending data)
  - http://localhost:9090/ (prometheus)
  - http://localhost:9090/targets (check prometheus targets)
  - http://localhost:3000/ (grafana) - 
    - login(admin/admin)
    - add datasource now with details
      - name: catalogapi-prometheus
      - url: http://192.168.1.181:9090
    - add new dashboard
    - add new metrics which pulls from prometheus
      - example: system_cpu_usage
    


- notes 
  - micrometer is a dimensional-first metrics collection facade whose aim is to allow you to time, count, and gauge your code with a vendor neutral API. Through classpath and configuration, you may select one or several monitoring systems to export your metrics data to. Think of it like SLF4J, but for metrics!
  - prometheus iss used to record real-time metrics in a time series database (allowing for high dimensionality) built using a HTTP pull model, with flexible queries and real-time alerting.
  - more links related to prometheus
  => https://reflectoring.io/monitoring-spring-boot-with-prometheus/


- extras of catalogapi
  - in pom.xml, in dependencies, 2 entries below are used to send spring boot application data to prometheus and 
    io.micrometer, org.springframework.boot
    
   
#microservices part - 3
goal v3.0
- achieve a basic microservice implementation in spring boot application
goal v3.1
- deploy this spring boot application on ec2 instance 
- access the application on internet

technology stack
- aws ec2
- aws iam
- nginx 
- aws route 53


steps done at high level
- project, profileapi achieves us goal v3.0 exposing REST services at http://localhost:5000/profiles
- create a jar of this profile api through maven, command > maven package
notes: maven uses dependency-reduced-pom.xml file to create a jar with all dependent libraries) 
- now login to aws (free tier), do the following
	- create a new ec2 instance of type t2.micro
	- login to ec2 instance on ssh
	- install openjdk on ec2
		- command > sudo yum install java-1.8.0
	- copy the profile api jar created to ec2 instance
		- command > scp -i <xyz.pem> <jar file path>  ec2-user@<public dns name of ec2 instance>:~/
	- run the jar now 
		- comment > nohup java -jar profileapi.jar &
		notes: this application now runs on port 5000 because spring boot application's application.properties has server port as 5000 which tells it to run on 5000
	- check if the java application is up and running
		- command > curl -Is http://localhost:5000 | head -1
		notes: above command should return HTTP/1.1 200 
	- now access the application through public dns url in browser
		- <public dns name>:5000/profiles in browser
	

