package com.booktrack.librarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableCaching
@EnableScheduling
public class LibraryManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementSystemApplication.class, args);
        System.out.println("Library Book Management System Application-Demo Check - CodePipeline - Cost managed to 0");


    }
}
