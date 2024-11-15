package com.studenthub.api.Service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;

public class DotenvService {

     Dotenv dotenv;

    @Autowired
    public DotenvService(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    public void printEnvVariables() {
        String BDUrl = dotenv.get("BD_URL");
        String username = dotenv.get("BD_USERNAME");
        String password = dotenv.get("BD_PASSWORD");

        System.out.println("BD URL: " + BDUrl);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }
}
