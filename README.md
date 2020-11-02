## micro-service
```
Objetivo desse projeto

##Contruir uma API separada por Micro-Services

```
## Requisitos
```
Java 8

```

# Caso deseje utilizar docker 
```
docker-compose up
docker-compose down

```

## Para criar um pacote
```
# Ambiente dev: docker-compose up   
# mvn package -Pdev

# Ambiente prod:  mvn clean package -Prod

```

## Para iniciar o projeto siga a sequência
```
-Dspring.profiles.active=dev
 java -jar -Dspring.profiles.active=dev ct-application.jar

 -Dspring.profiles.active=prod
 java -jar -Dspring.profiles.active=prod ct-application.jar

```


## Acesse seu Ec2 na aws siga:
```
deploy Ec2: https://www.youtube.com/watch?v=tNi8ymBdAh4
Para instalar o jar na aws:
        1-
        chmod 400 aw-spring-amazon-aws.pem
        2-
        scp -i aw-spring-amazon-aws.pem target/ct-application.jar ubuntu@ec2-3-138-182-18.us-east-2.compute.amazonaws.com:~

        3-
        para fazer o login na aws:
        ssh -i aw-spring-amazon-aws.pem ubuntu@ec2-3-138-182-18.us-east-2.compute.amazonaws.com

```
## RDS
```
Crie um RDS na aws:
https://www.youtube.com/watch?v=XwJQbDvkOyM

https://www.youtube.com/watch?v=g3gKF_Li1WM

```

#### Para utilizar o Elastic Beanstalk siga:
```
deploy Elastic Beanstalk:  https://www.youtube.com/watch?v=6SadWaJrtnY
```
#### Url localhost
## GET
```
Curl GET http://localhost:8080/contato/list
Resp:
{
    "content": [
        {
            "name": "Adriano",
            "fone": "553496521488"
        },
        {
            "name": "Eduardo",
            "fone": "553496521477"
        },
        {
            "name": "Laura",
            "fone": "553496521424"
        },
        {
            "name": "Jessica",
            "fone": "553499232415"
        }
    ],
    "last": true,
    "totalPages": 1,
    "totalElements": 4,
    "size": 20,
    "number": 0,
    "sort": null,
    "first": true,
    "numberOfElements": 4
}
```
## POST
```
Curl POST http://localhost:8080/contato/createde
Req:
{
    "name":"Jessica",
    "fone":"553499232415"
}
Resp: status 201 CREATED
{
    "id":"1"    
}
```
## PUT
```
Curl PUT http://localhost:8080/contato/edit/1
Req: 
{
    "name":"Jessica",
    "fone":"553499232415"
}
Resp: status 202 ACCEPTED
{
    "id":"1"    
}
```
## DELETE
```
Curl DELETE http://localhost:8080/contato/excluir/1
Resp:
status: 204    NO_CONTENT

```

#### Url Ec2
```
Curl GET ec2-3-138-182-18.us-east-2.compute.amazonaws.com:8080/contato/list

```
#### UElastic Beanstalk
```
http://url do elasticbeanstalk criada na aws/contato/list

```
