package yibitong;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class FinanceApplication {

    @GetMapping("/service")
    public String service(){
        System.out.println(":333333333333333333333333");
        return "service";
    }

    public static void main( String[] args ) {
        SpringApplication.run(FinanceApplication.class,args);
    }
}
