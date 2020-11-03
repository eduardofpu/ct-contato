package org.com.appl.contato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/**
 * 1- deploy Ec2: https://www.youtube.com/watch?v=tNi8ymBdAh4
 *
 * 2- deploy Elastic Beanstalk:  https://www.youtube.com/watch?v=6SadWaJrtnY
 ----------------------------------------------------------------------------------------------------------------------
 https://pt.stackoverflow.com/questions/287187/usar-profiling-do-maven-ou-do-spring-boot
 Execute o Package para prod no promp de comando:  mvn clean package -Prod

 ----------------------------------------------------------------------------------------------------------------------
Para instalar o jar na aws:
        1-
        chmod 400 aw-spring-amazon-aws.pem
        2-
        scp -i aw-spring-amazon-aws.pem target/ct-application.jar ubuntu@ec2-3-138-182-18.us-east-2.compute.amazonaws.com:~

        3-
        para fazer o login na aws:
        ssh -i aw-spring-amazon-aws.pem ubuntu@ec2-3-138-182-18.us-east-2.compute.amazonaws.com

 ----------------------------------------CONFIGURAÇÕES NA PRIMERIA VEZ--------------------------------------------------
        ubuntu@ip-172-31-27-136:~$ sudo su -

        sudo apt-get install language-pack-pt --yes

        sudo apt-get update
        sudo apt-get install default-jdk
        java -version
 ----------------------------------------------------------------------------------------------------------------------
        4-
        java -jar ct-application.jar

 ------------------------------Config propiles--------------------------------------------------------------------------
 -Dspring.profiles.active=dev
 java -jar -Dspring.profiles.active=dev ct-application.jar

 -Dspring.profiles.active=prod
 java -jar -Dspring.profiles.active=prod ct-application.jar


 ---------------------------OUTRA OPÇÃO DE  EMPACOTAMENTO --------------------------------------------------------------
 # Ambiente dev: docker-compose up   mvn package -Pdev
 # Ambiente prod:  mvn clean package -Prod

 Após o package use o comando na aws: java -jar ct-application.jar

 ---------------------------URL-----------------------------------------------------------------------------------------
 ec2-3-138-182-18.us-east-2.compute.amazonaws.com:8080/contato/list
**/
@ComponentScan(basePackages = {
        "org.com.api.contato.representation",
        "org.com.appl.contato.controller",
        "org.com.cmn.contato.exception",
        "org.com.sv.contato.service",
        "org.com.dm.contato.impl"})
@EntityScan(basePackages = {"org.com.ct.md.contato.entity"})
@EnableJpaRepositories(basePackages = {"org.com.rp.contato.repository"})
@SpringBootApplication
public class ContatoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContatoApplication.class,args);
    }
}
