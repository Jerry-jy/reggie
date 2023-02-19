package com.jerry.reggie.dto;

import com.jerry.reggie.entity.Setmeal;
import com.jerry.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
