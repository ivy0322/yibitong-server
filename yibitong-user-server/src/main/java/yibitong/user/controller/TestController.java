package yibitong.user.controller;

import com.yibitong.entity.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yibitong.user.service.TestUserService;

import java.util.Date;


@RestController
@RequestMapping("/test")
public class TestController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TestUserService userService;

    @RequestMapping("/save")
    public String save(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("测试项目");
        userEntity.setPassword("123455");
        userService.insert(userEntity);
        return "success";
    }
}
