package uy.com.sofka.tallerReactiva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TallerReactivaApplication {

    public static void main(String[] args) {

        SpringApplication.run(TallerReactivaApplication.class, args);
    }

}
