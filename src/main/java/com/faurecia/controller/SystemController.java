package com.faurecia.controller;


import com.faurecia.dao.BaseDao;
import com.faurecia.model.DMenuEntity;
import com.faurecia.model.DRoleEntity;
import com.faurecia.vo.Author;
import com.faurecia.vo.BaseResult;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */
@Controller(value = "/sys")
public class SystemController {
    @Autowired
    private BaseDao dao;


    /**
     * 获取目录
     * TODO 目录页没有对功能限制到按钮级别 对权限没有限制
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMenuByLevel",produces = "text/json;charset=UTF-8")
    public String getMenuByLevel(HttpSession session)throws  Exception{
        BaseResult result = new BaseResult();
        try{
            String role = String.valueOf(session.getAttribute("role"));
            String scope = dao.findById(DRoleEntity.class,Integer.parseInt(role)).getScope();
            List<DMenuEntity> list = dao.findAll("from d_menu d where 1=1 and id in ("+scope+") and type=1 ORDER BY id ASC");
            result.setBizObject(list);
        }catch (Exception e){
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

    /**
     * 获取session
     * TODO session 细分应该有更多的属性
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getSession",produces = "text/json;charset=UTF-8")
    public String getSession(HttpSession session) throws Exception{
        BaseResult result = new BaseResult();
        Author vo = new Author();
        try {
            if(null==session.getAttribute("adminId")){
                result.setStatus(-1);
                return new Gson().toJson(result);
            }
            DRoleEntity role=dao.findById(DRoleEntity.class,Integer.parseInt(String.valueOf(session.getAttribute("role"))));
            vo.setAdminId(Integer.parseInt(String.valueOf(session.getAttribute("adminId"))));
            vo.setRole(String.valueOf(session.getAttribute("role")));
            vo.setUserName(String.valueOf(session.getAttribute("userName")));
            vo.setScope(role.getScope());
            result.setBizObject(vo);
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

    /**
     * 退出登录 移除session
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/removeSession",produces = "text/json;charset=UTF-8")
    public String removeSession(HttpSession session) throws Exception{
        BaseResult result = new BaseResult();
        try {
           session.removeAttribute("adminId");
            session.removeAttribute("role");
            session.removeAttribute("userName");
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

}
