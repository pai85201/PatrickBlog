package com.paitrick.blog;
import org.mybatis.spring.annotation.MapperScan; import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication @MapperScan("com.paitrick.blog") public class BlogApplication { public static void main(String[] args){SpringApplication.run(BlogApplication.class,args);} }
