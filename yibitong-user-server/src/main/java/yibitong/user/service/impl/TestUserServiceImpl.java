package yibitong.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yibitong.entity.user.UserEntity;
import org.springframework.stereotype.Component;
import yibitong.user.mapper.UserMapper;
import yibitong.user.service.TestUserService;

@Component
public class TestUserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements TestUserService{

}
