package com.example.demojpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoJpaApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(DemoJpaApplication.class, args);
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception{

        //Una forma de generar contraseñas mas seguras es con Bcrypt, aunque se deben insertar así en base de datos
//        String password = "12345";
//
//        for(int i=0; i<2; i++){
//            String bcryptpassword = passwordEncoder.encode(password);
//            System.out.println(bcryptpassword);
//        }
    }


}
