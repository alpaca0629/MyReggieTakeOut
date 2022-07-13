package com.alpaca.reggie.controller;

import com.alpaca.reggie.common.R;
import com.alpaca.reggie.entity.User;
import com.alpaca.reggie.service.UserService;
import com.alpaca.reggie.utils.SMSUtils;
import com.alpaca.reggie.utils.ValidateCodeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            // 生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            // 发送短信
            // SMSUtils.sendMessage("外卖", "", phone, code);
            // 需要将生成的验证码保存到Session
//            session.setAttribute("phone", phone);
//            session.setAttribute("code", code);

            // 将生成的验证码缓存到Redis，并且设置有效期为5分钟
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(phone, code, 5, TimeUnit.MINUTES);
            return R.success("手机验证码短信发送成功");
        }
        return R.error("手机验证码短信发送失败");
    }

    /**
     * 移动端用户登录
     *
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        // 获取手机号
        String phone = map.get("phone").toString();
        // 获取验证码
        String code = map.get("code").toString();
        // 从Session中获取保存的验证码
//        String sessionPhone = (String) session.getAttribute("phone");
//        String sessionCode = (String) session.getAttribute("code");
        // 从redis中获取缓存的验证码
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String redisCode = (String) valueOperations.get(phone);
        // 进行验证码比对(页面提交的验证码和Session中的验证码比对)
        if (redisCode != null && redisCode.equals(code)) {
            // 如果能够比对成功，说明登录成功了
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User :: getPhone, phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                // 判断当前用户是否为新用户，如果是新用户就自动注册
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }

        return R.error("登录失败");
    }
}
