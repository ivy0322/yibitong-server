package yibitong;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class InformationApplication {

    @GetMapping("/service")
    public String service(){
        System.out.println(":2222222222222222222222222222");
        return "service";
    }

    public static void main( String[] args ) {
        SpringApplication.run(InformationApplication.class,args);
    }
}
