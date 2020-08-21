# PhotoApp Microservice Project
  
## PhotoAppApiConfigServer


#### Build RabbitMQ Docker Image for PhotoAppApiConfigServer

  - docker run -d --name rabbit-name-management -p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 rabbitmq:3-management
  - Go to RabbitMQ dashboard and create users: <INSTANCE_PUBLIC_IP_ADDRESS>:15672

#### Build Docker Image for PhotoAppApiConfigServer
  - docker login --username=kevbot55
  - ./mvnw clean
  - ./mvnw package
  - docker build --tag=config-server --force-rm=true .
  - create dockerhub repository online with your account
  - docker tag <IMAGE_ID> <DOCKERHUB_NAME_OF_REPO>
  - docker push <DOCKERHUB_NAME_OF_REPO>
  
#### Run Docker Image for PhotoAppApiConfigServer
  - ssh into aws instance
  - docker login --username=kevbot55
  - sudo yum update
  - sudo yum install docker 
  - sudo service docker start
  - sudo usermod -a -G docker ec2-user
  - docker run --env=spring.rabbitmq.host=172.17.0.3 -p 8012:8012 kevbot55/config-server  (use Docker inspect to get rabbitmq.host)
  
 #### Set inbound ports for PhotoAppApiConfigServer
 
  ![GitHub Logo](/images/config-inbound.png)
 
 #### Test endpoints for PhotoAppApiConfigServer
 
 - See PhotoApp.postman_collection.json
 - getUserConfigDocker
 - getAppConfigDocker
 
 
## Eureka Server

#### Build Docker Image for Eureka Server
  - docker login --username=kevbot55
  - ./mvnw clean
  - ./mvnw package
  - docker build --tag=km-eureka-server --force-rm=true .
  - create dockerhub repository online with your account
  - docker tag <IMAGE_ID> <DOCKERHUB_NAME_OF_REPO>
  - docker push <DOCKERHUB_NAME_OF_REPO>
  
#### Run Docker Image for Eureka Server
  - ssh into aws instance
  - docker login --username=kevbot55
  - sudo yum update
  - sudo yum install docker 
  - sudo service docker start
  - sudo usermod -a -G docker ec2-user
  - docker run -d -p 8010:8010 kevbot55/config-server 
  
 #### Set inbound ports for Eureka Server
 
  ![GitHub Logo](/images/eureka-inbound.png)
 
 #### Check Dashboard for Eureka Server
   - <PUBLIC_IP_ADDRESS_OF_EUREKA_INSTANCE>:8010
    
    
## Zuul Server
  
#### Build Docker Image for Zuul Server
  - docker login --username=kevbot55
  - ./mvnw clean
  - ./mvnw package
  - docker build --tag=km-zuul_api-gateway --force-rm=true .
  - create dockerhub repository online with your account
  - docker tag <IMAGE_ID> <DOCKERHUB_NAME_OF_REPO>
  - docker push <DOCKERHUB_NAME_OF_REPO>
  
#### Run Docker Image for Zuul Server
  - ssh into aws instance
  - docker login --username=kevbot55
  - sudo yum update
  - sudo yum install docker 
  - sudo service docker start
  - sudo usermod -a -G docker ec2-user
  - docker run -d -p 8010:8010 kevbot55/km-zuul-api-gateway
  
 #### Set inbound ports for Zuul Server
 
  ![GitHub Logo](/images/zuul-inbound.png)
 
 #### Check Dashboard for Zuul Server
   - <PUBLIC_IP_ADDRESS_OF_ZUUL_INSTANCE>:8010
   
   
## MYSQL SERVER
  - ssh into aws instance
  - docker login --username=kevbot55
  - sudo yum update
  - sudo yum install docker 
  - sudo service docker start
  - sudo usermod -a -G docker ec2-user
  - docker run -d -p 3306:3306 --name mysql-docker-container -e MYSQL_ROOT_PASSWORD=kevin -e MYSQL_DATABASE=photo_app -e MYSQL_USER=kevin -e MYSQL_PASSWORD=kevin                   -v /var/lib/mysql:/var/lib/mysql  mysql:5.7.31
  
   #### Set inbound ports for Zuul Server
 
  ![GitHub Logo](/images/mysql-inbound.png)
  
  ## Kibabana + Elastic Search
  - ssh into aws instance
  - docker login --username=kevbot55
  - sudo yum update
  - sudo yum install docker 
  - docker network create --driver bridge api-network
  - docker run -d -v esdata1:/usr/share/elasticsearch/data --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" 
                  --network api-network elasticsearch:7.3.0
  - docker run -d --network api-network -p 5601:5601 kibana:7.3.0

#### ELastic + Kibana inbound ports

  ![GitHub Logo](/images/elastic-kibana-inbound.png)
  

## Albums Microservice

#### Build Docker Image for Albums Microservice
  - docker login --username=kevbot55
  - ./mvnw clean
  - ./mvnw package
  - docker build --tag=km-albums-microservice --force-rm=true .
  - create dockerhub repository online with your account
  - docker tag <IMAGE_ID> <DOCKERHUB_NAME_OF_REPO>
  - docker push <DOCKERHUB_NAME_OF_REPO>
  
#### Run Docker Image for Albums Microservice + Logstash
  - ssh into aws instance
  - docker login --username=kevbot55
  - sudo yum update
  - sudo yum install docker 
  - sudo service docker start
  - sudo usermod -a -G docker ec2-user
  - docker run -d -e "logging.file=/api-logs/albums-ws.log" -v /home/ec2-user/api-logs:/api-logs --network host kevbot55/km-albums-microservice
  
 #### Logstash
  - Create dockerfile, logstash.yml and logstash.conf. (See LogstashConfig folder of project)
  - docker build --tag=logstash --force-rm=true .
  - create dockerhub repository online with your account
  - docker tag <IMAGE_ID> <DOCKERHUB_NAME_OF_REPO>
  - docker push <DOCKERHUB_NAME_OF_REPO>
  - docker run -d --name=users-ws-logstash -v /home/ec2-user/api-logs:/api-logs kevbot55/km-users-microservice-logstash
  
  
## Users Microservice

#### Build Docker Image for Albums Microservice
  - docker login --username=kevbot55
  - ./mvnw clean
  - ./mvnw package
  - docker build --tag=km-users-microservice --force-rm=true .
  - create dockerhub repository online with your account
  - docker tag <IMAGE_ID> <DOCKERHUB_NAME_OF_REPO>
  - docker push <DOCKERHUB_NAME_OF_REPO>
  
#### Run Docker Image for Albums Microservice + Logstash
  - ssh into aws instance
  - docker login --username=kevbot55
  - sudo yum update
  - sudo yum install docker 
  - sudo service docker start
  - sudo usermod -a -G docker ec2-user
  - docker run -d -e "spring.cloud.config.uri=http://172.31.32.241:8012" -e "spring.rabbitmq.host=172.17.0.2" -e "eureka.client.serviceUrl.defaultZone=http://test:test@172.31.41.218:8010/eureka" -e "spring.datasource.url=jdbc:mysql://172.31.33.80:3306/photo_app?serverTimezone=UTC" --network host -e "logging.file=/api-logs/users-ws.log" -e "spring.profiles.active=development" -v /home/ec2-user/api-logs:/api-logs kevbot55/km-users-microservice
 
 #### Logstash
  - Create dockerfile, logstash.yml and logstash.conf. (See LogstashConfig folder of project)
  - docker build --tag=logstash --force-rm=true .
  - create dockerhub repository online with your account
  - docker tag <IMAGE_ID> <DOCKERHUB_NAME_OF_REPO>
  - docker push <DOCKERHUB_NAME_OF_REPO>
  - docker run -d --name=users-ws-logstash -v /home/ec2-user/api-logs:/api-logs kevbot55/km-users-microservice-logstash

#### Zipkin on Config Server

  - docker run -d -p 9411:9411 --network host openzipkin/zipkin
  - Add to <filename>,properties
        spring.zipkin.base-url=http://172.31.32.241:9411
        spring.zipkin.sender.type=web
        spring.sleuth.sampler.probability=1

## Useful Commands
  
  #### Elastic Search
  
 - http://ec2-54-217-54-156.eu-west-1.compute.amazonaws.com:9200/_cat/indices
 - kevin@Babage-Machine:/usr/share/elasticsearch$ ./bin/elasticsearch
 - http://localhost:9200/albums-ws-2020.08.10/_search?q=*&format&pretty
 - http://localhost:9200/users-ws-2020.08.10/_search?q=*&format&pretty
   
   
 #### Logstash
   
 - kevin@Babage-Machine:/usr/share/logstash$ bin/logstash -f simple-config.conf 
   
 #### RabbitMQ
   
- sudo service rabbitmq-server start
- sudo service rabbitmq-server stop

 #### MYSQL
   
- sudo service mysql start
- sudo service mysql stop
- mysql -u kevin -p

 #### Zipkin
    
- kevin@Babage-Machine:~$ java -jar zipkin.jar

 #### KeyGen

- keytool -genkey -alias apiEncryptionKey -keyalg RSA -dname "CN=Kevin Mcinerney,OU=API Development,O=appsdeveloperblog.com,L=Ottawa,S=ON,C=CA" -keypass 1q2w3e4r            -keystore apiEncryption.jks -storepass 1q2w3e4r

 #### Kibana
    
- kevin@Babage-Machine:~/kibana-4.1.1-linux-x64$ bin/kibana


#### To run in chosen environment (dev prod...)
./mvnw spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=production


