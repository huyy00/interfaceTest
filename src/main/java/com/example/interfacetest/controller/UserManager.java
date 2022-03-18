package com.example.interfacetest.controller;

import com.example.interfacetest.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Api(value = "v1")
@Slf4j
@RestController
@RequestMapping("v1")
public class UserManager {

    @Autowired
    private SqlSessionTemplate template;

    /**
     * 登录
     */
    @ApiOperation(value = "登录",httpMethod ="POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public boolean login(HttpServletResponse response, @RequestBody User user){
        int i=template.selectOne("login",user);
        Cookie cookie=new Cookie("login","true");
        response.addCookie(cookie);
        log.info("查询到的结果是"+i);
        if (i==1){
            System.out.println("用户名是："+user.getUserName());
            log.info("登录的用户名是："+user.getUserName());
            return true;
        }
        return false;
    }

    /**
     * 添加用户
     * @return
     */
    @ApiOperation(value = "添加用户",httpMethod = "POST")
    @PostMapping("addUser")
    public boolean addUser(HttpServletRequest request, @RequestBody User user){
        Boolean i=verifyCookies(request);
        int result=0;
        if (i==true){
            result=template.insert("addUser",user);
        }
        if (result>0){
            log.info("添加用户成功"+user);
            System.out.println("添加用户成功");
            return true;
        }
        return false;
    }

    /**
     * 修改用户信息
     * @return
     */
    @ApiOperation(value = "修改用户",httpMethod = "POST")
    @PostMapping("updateUser")
    public int updateUser(HttpServletRequest request,@RequestBody User user){
        Boolean i=verifyCookies(request);
        int result=0;
        if (i==true){
            result=template.update("updateUser",user);
        }
        if (result>0){
            log.info("更新用户成功"+user);
            System.out.println("更新的用户是："+user.getId()+"更新的用户数量是："+result);
            return result;
        }
        return result;

    }


    /**
     * 获取用户列表
     * @param request
     * @param user
     * @return
     */
    @ApiOperation(value = "获取用户列表",httpMethod = "POST")
    @PostMapping("getUserList")
    public List<User> getUserList(HttpServletRequest request, @RequestBody User user){
        Boolean i=verifyCookies(request);
        List<User> userList;
        if (i==true){
            userList=template.selectList("getUserList",user);
            log.info("获取到的用户数量是："+userList.size());
            return userList;
        }else {
            return null;
        }
    }

    /**
     * 校验cookies
     * @param request
     * @return
     */
    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies=request.getCookies();
        if (Objects.isNull(cookies)){
            return false;
        }
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("login")&&cookie.getValue().equals("true")){
                log.info("cookies验证通过");
                return true;
            }
        }
        return false;
    }
}
