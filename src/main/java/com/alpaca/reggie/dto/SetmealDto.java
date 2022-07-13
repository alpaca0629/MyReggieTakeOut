package com.alpaca.reggie.dto;

import com.alpaca.reggie.entity.Setmeal;
import com.alpaca.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
