package com.alpaca.reggie.service.impl;

import com.alpaca.reggie.entity.DishFlavor;
import com.alpaca.reggie.mapper.DishFlavorMapper;
import com.alpaca.reggie.service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
