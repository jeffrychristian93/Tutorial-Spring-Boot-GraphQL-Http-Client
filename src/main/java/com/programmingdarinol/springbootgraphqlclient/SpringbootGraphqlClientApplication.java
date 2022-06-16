package com.programmingdarinol.springbootgraphqlclient;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.HttpGraphQlClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringbootGraphqlClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootGraphqlClientApplication.class, args);
  }

  @Bean
  ApplicationRunner applicationRunner(){
    return args -> {

      HttpGraphQlClient httpGraphQlClient = HttpGraphQlClient.builder()
          .url("http://localhost:8080/graphql")
          .build();

      httpGraphQlClient.document("""
            {
              getStudents {
                id
                name
              }
            }
          """)
          .retrieve("getStudents")
          .toEntityList(StudentGraphqlResponse.class)
          .flatMapMany(Flux::fromIterable)
          .subscribe(System.out::println);
    };
  }
}

record StudentGraphqlResponse(String id, String name){}