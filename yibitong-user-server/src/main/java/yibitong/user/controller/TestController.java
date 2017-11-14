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
        userEntity.setUser_phone("15923512161");
        userEntity.setPassword("123455");
        userEntity.setJpushtag("1111");
        userEntity.setCreatetime(new Date().getTime());
        userEntity.setForbidden(1);
        userEntity.setRecommend(1);
        userEntity.setReg_ip("127.0.0.1");
        userEntity.setUser_age(10);
        userEntity.setUser_consult(100);
        userEntity.setUser_type(1);
        userEntity.setForbidden(0);
        userEntity.setRecommend(11);
        userEntity.setUser_email("55555@qq.com");
        userEntity.setUser_sex(1);
        userEntity.setUser_bespeak(100);
        userService.insert(userEntity);
        return "success";
    }
}
