package yibitong.user.controller;

import com.yibitong.entity.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yibitong.user.config.RedisUtils;
import yibitong.user.service.TestUserService;

import java.util.Date;


@RestController
@RequestMapping("/test")
public class TestController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisUtils redis;

    @Autowired
    TestUserService userService;

    @RequestMapping("/save")
    public String save(){
        logger.info("进入测试项目");
        UserEntity userEntity = new UserEntity();
        userEntity.setUser_id(12344);
        userEntity.setUsername("测试项目");
        userEntity.setPassword("123455");
        userService.insert(userEntity);

        redis.set("chenjun","123456");
        return "success";
    }
}
