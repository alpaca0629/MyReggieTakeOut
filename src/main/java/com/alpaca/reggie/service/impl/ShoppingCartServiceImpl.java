package com.alpaca.reggie.service.impl;

import com.alpaca.reggie.entity.ShoppingCart;
import com.alpaca.reggie.mapper.ShoppingCartMapper;
import com.alpaca.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
