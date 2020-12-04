# taskmanager-service

Taskmanager enables user to register themselves  and create task with parent task,priority,start date and end date

Users can create, modify and get list of task.

Tested on Google Cloud Vm with Ubuntu

1. Docker [https://docs.docker.com/engine/install/ubuntu/](https://docs.docker.com/engine/install/ubuntu/)
2. Docker-Compose [https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/)

3. Installation https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-16-04



Commands

> **docker-compose build**

> **docker-compose up**

> **docker-compose stop**

> **docker images ls**

> **docker container ls -a**

> **docker rm container-name**

> **docker rmi image-name1,..**

To check logs
> **docker logs container-name**

To run mysql client inside mysql container 
> **docker run -it --network taskmanager-service_default --rm mysql mysql -htaskmanager-service_mysql-docker-container_1 -uroot -p**
> **SHOW DATABASES;**
> **show tables;**

