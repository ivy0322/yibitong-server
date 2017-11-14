package yibitong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@MapperScan("yibitong.user.mapper.**")
public class UserApplication {

    @GetMapping("/service")
    public String service(){
        System.out.println(":11111111111111111111111111111");
        return "service";
    }

    public static void main( String[] args ) {
        SpringApplication.run(UserApplication.class,args);
    }
}
