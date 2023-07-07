package uk.co.aaronburt.ipmi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IpmiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IpmiApplication.class, args);
        Dotenv dotenv = Dotenv.load();

    }

}
