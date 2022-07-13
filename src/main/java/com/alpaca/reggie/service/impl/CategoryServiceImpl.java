package com.alpaca.reggie.service.impl;

import com.alpaca.reggie.common.CustomException;
import com.alpaca.reggie.entity.Category;
import com.alpaca.reggie.entity.Dish;
import com.alpaca.reggie.entity.Setmeal;
import com.alpaca.reggie.mapper.CategoryMapper;
import com.alpaca.reggie.service.CategoryService;
import com.alpaca.reggie.service.DishService;
import com.alpaca.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类，删之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        // 查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据id进行查询
        dishQueryWrapper.eq(Dish::getCategoryId, id);
        int dishCount = dishService.count(dishQueryWrapper);
        if (dishCount > 0) {
            // 已关联菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，无法删除");
        }

        // 查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据id进行查询
        setmealQueryWrapper.eq(Setmeal::getCategoryId, id);
        int setmealCount = setmealService.count(setmealQueryWrapper);
        if (setmealCount > 0) {
            throw new CustomException("当前分类下关联了套餐，无法删除");
        }
        // 正常删除分类
        super.removeById(id);
    }
}
