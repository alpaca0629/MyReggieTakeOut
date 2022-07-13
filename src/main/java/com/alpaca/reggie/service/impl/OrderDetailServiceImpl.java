package com.alpaca.reggie.service.impl;

import com.alpaca.reggie.entity.OrderDetail;
import com.alpaca.reggie.mapper.OrderDetailMapper;
import com.alpaca.reggie.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
