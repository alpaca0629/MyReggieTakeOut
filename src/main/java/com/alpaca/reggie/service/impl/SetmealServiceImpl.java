package com.alpaca.reggie.service.impl;

import com.alpaca.reggie.common.CustomException;
import com.alpaca.reggie.dto.SetmealDto;
import com.alpaca.reggie.entity.Setmeal;
import com.alpaca.reggie.entity.SetmealDish;
import com.alpaca.reggie.mapper.SetmealMapper;
import com.alpaca.reggie.service.SetmealDishService;
import com.alpaca.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        // 保存套餐基本信息
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        // 保存套餐和菜品的关联信息
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        // 查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal :: getId, ids);
        queryWrapper.eq(Setmeal :: getStatus, 1);

        int count = this.count(queryWrapper);
        if (count > 0) {
            // 如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中， 不能删除");
        }

        // 如果可以删除，先删除套餐表中的数据--Setmeal
        this.removeByIds(ids);
        // 删除关系表中的数据--SetmealDish
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish :: getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);
    }
}
