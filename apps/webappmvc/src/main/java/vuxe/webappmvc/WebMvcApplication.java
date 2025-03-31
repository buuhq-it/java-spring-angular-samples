package vuxe.webappmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "vuxe.webappmvc",
        "vuxe.infrastructure",
        "vuxe.application",
        "vuxe.domain"
})
public class WebMvcApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebMvcApplication.class, args);
    }
}
