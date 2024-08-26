//package com.Mercurius.ApiGateway.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.stereotype.Component;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//public class BeanChecker implements CommandLineRunner {
//
//    @Autowired
//    private ClientRegistrationRepository clientRegistrationRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (clientRegistrationRepository != null) {
//           log.info("ClientRegistrationRepository is initialized.");
//        } else {
//           log.error("ClientRegistrationRepository is null.");
//        }
//    }
//}
