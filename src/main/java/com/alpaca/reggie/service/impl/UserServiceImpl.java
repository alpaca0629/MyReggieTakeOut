package com.alpaca.reggie.service.impl;

import com.alpaca.reggie.entity.User;
import com.alpaca.reggie.mapper.UserMapper;
import com.alpaca.reggie.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
