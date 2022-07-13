package com.alpaca.reggie.service.impl;

import com.alpaca.reggie.entity.Employee;
import com.alpaca.reggie.mapper.EmployeeMapper;
import com.alpaca.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
