package com.alpaca.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.alpaca.reggie.entity.AddressBook;
import com.alpaca.reggie.mapper.AddressBookMapper;
import com.alpaca.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
