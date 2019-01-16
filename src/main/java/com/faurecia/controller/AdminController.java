package com.faurecia.controller;

import com.faurecia.dao.BaseDao;
import com.faurecia.model.DManagerEntity;
import com.faurecia.model.DMenuEntity;
import com.faurecia.model.DRoleEntity;
import com.faurecia.vo.*;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/8/24.
 */
@Controller(value = "/admin")
@Component
public class AdminController {

    @Autowired
    private BaseDao dao;
    @Value("${env}")
    private String env;



    /**
     * 用户的登录
     * TODO 密码加密 、session 管理
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getManagerList", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    public String getManagerList(Author vo) throws Exception{
        BaseResult result = new BaseResult();
        try {
            String sqlStr = "from d_manager where 1=1 and isDel=1 ";
            int records=dao.findAll(sqlStr).size();
            result.setRecords(records);
            result.setTotal(records/vo.getRows()+((records%vo.getRows())==0?0:1));
            if(null!=vo.getSidx()&&!vo.getSidx().equals("")){
                sqlStr+=" order by "+vo.getSidx();
            }else{
                sqlStr+=" order by id ";
            }
            if(null!=vo.getSord()){
                sqlStr+=" "+vo.getSord();
            }
            List list =dao.findAllByPage(sqlStr,vo.getRows()*(vo.getPage()-1),vo.getRows());
            result.setRows(list);
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

    public boolean checkDoubleName(String name){
        List list =dao.findAll("from d_manager where 1=1 and isDel=1 and userName='"+name+"'");
        return list.size()>=1;
    }

    /**
     * 权限修改
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/adminListOption",produces = "text/json;charset=UTF-8",method = RequestMethod.POST )
    public  String adminListOption(Author vo)throws Exception{
        BaseResult result = new BaseResult();
        try {
            switch (vo.getOper()){
                case "edit": {
                    DManagerEntity manager = dao.findById(DManagerEntity.class,Integer.parseInt(String.valueOf(vo.getId())));
                    if(!vo.getUserName().equals(manager.getUserName())&&checkDoubleName(vo.getUserName())){
                        result.setStatus(2);
                        result.setMessage("系统中已有此命名");
                        return new Gson().toJson(result);
                    }

                    manager.setUserName(vo.getUserName());
                    manager.setPwd(vo.getPwd());
                    manager.setRole(vo.getRole());
                    dao.update(manager);
                    break;
                }
                case "del":{
                    List<String> ids = Arrays.asList(String.valueOf(vo.getId()).split(","));
                    for (String id:ids){
                        DManagerEntity role = dao.findById(DManagerEntity.class,Integer.parseInt(id));
                        role.setIsDel(0);
                        role.setDelTime(System.currentTimeMillis());
                        dao.update(role);
                    }
                    break;
                }
                case "add":{
                    if(checkDoubleName(vo.getUserName())){
                        result.setStatus(2);
                        result.setMessage("系统中已有此命名");
                        return new Gson().toJson(result);
                    }
                    DManagerEntity  manager = new DManagerEntity();
                    manager.setUserName(vo.getUserName());
                    manager.setPwd(vo.getPwd());
                    manager.setRole(vo.getRole());
                    manager.setCreateTime(System.currentTimeMillis());
                    manager.setIsDel(1);
                    dao.save(manager);
                    break;
                }
                default:{
                    break;
                }
            }
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }



    /**
     * 用户的登录
     * TODO 密码加密 、session 管理
     * @param vo
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login4admin", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    public String login4admin(Author vo, HttpSession session) throws Exception{
        BaseResult result = new BaseResult();
        System.out.print(env);

        try {
            DManagerEntity manager = new DManagerEntity();
            if (null != vo.getUserName() && !vo.getUserName().equals("") && null != vo.getPwd() && !vo.getPwd().equals("")) {
                List<DManagerEntity> list = dao.findAll("from d_manager where 1=1 and isDel=1 and userName='" + vo.getUserName() + "' and " + "pwd='" + vo.getPwd() + "'");
                if (list.size() == 1) {
                    manager = list.get(0);
                    vo.setRole(manager.getRole());
                    session.setAttribute("userName", list.get(0).getUserName());
                    session.setAttribute("adminId", list.get(0).getId());
                    session.setAttribute("role", list.get(0).getRole());
                    System.out.println(session.getAttribute("adminName"));
                    result.setBizObject(vo);
                } else {
                    result.setStatus(2);
                    result.setMessage("账号或密码不匹配！");
                }
            } else {
                result.setStatus(2);
                result.setMessage("请输入账号密码！");
            }
        } catch (Exception e) {
            result.setStatus(-1);
            throw new RuntimeException(e);
        }
        return new Gson().toJson(result);
    }

    /**
     * 检查旧密码是否正确
     * TODO 密码加密解密
     * @param author
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkOldPwd", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    public String checkOldPwd(Author author) throws Exception {
        BaseResult result = new BaseResult();
        try {
            DManagerEntity admin = dao.findById(DManagerEntity.class, author.getAdminId());
            if (null != admin && admin.getPwd().equals(author.getPwd())) {
            } else {
                result.setStatus(2);
            }
        } catch (Exception e) {
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }


    /**
     * 更改密码
     * @param author
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/changePWD", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    public String changePWD(Author author) throws Exception {
        BaseResult result = new BaseResult();
        try {
            DManagerEntity admin = dao.findById(DManagerEntity.class, author.getAdminId());
            admin.setPwd(author.getPwd());
            dao.update(admin);
        } catch (Exception e) {
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }


    /**
     * 获取所有角色类型 供下拉列表使用
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getAllRoleList",produces = "text/json;charset=UTF-8",method = RequestMethod.POST )
    public  String getAllRoleList(BaseArg vo) throws Exception{
        BaseResult result = new BaseResult();
        try {
            String sqlStr = "from d_role where 1=1 and isDel=1 order by id desc";
            List<DRoleEntity> list =dao.findAll(sqlStr);
            HashMap<Integer,String> res = new HashMap<>();
            for (DRoleEntity role:list){
                res.put(role.getId(),role.getName());
            }
            result.setBizObject(res);
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

    /**
     * 获取权限列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRoleList", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    public String getRoleList(RoleVO vo)throws Exception {
        BaseResult result = new BaseResult();
        try {
            String sqlStr = "from d_role where 1=1 and isDel=1 ";
            int records=dao.findAll(sqlStr).size();
            result.setRecords(records);
            result.setTotal(records/vo.getRows()+((records%vo.getRows())==0?0:1));
            if(null!=vo.getSidx()&&!vo.getSidx().equals("")){
                sqlStr+=" order by "+vo.getSidx();
            }else{
                sqlStr+=" order by id ";
            }
            if(null!=vo.getSord()){
                sqlStr+=" "+vo.getSord();
            }
            List list =dao.findAllByPage(sqlStr,vo.getRows()*(vo.getPage()-1),vo.getRows());
            result.setRows(list);
        } catch (Exception e) {
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

    /**
     * 权限修改
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/roleListOption",produces = "text/json;charset=UTF-8",method = RequestMethod.POST )
    public  String roleListOption(RoleVO vo) throws Exception{
        BaseResult result = new BaseResult();
        try {
            switch (vo.getOper()){
                case "edit": {
                    DRoleEntity type = dao.findById(DRoleEntity.class,Integer.parseInt(String.valueOf(vo.getId())));
                    type.setDis(vo.getDis());
                    type.setName(vo.getName());
                    dao.update(type);
                    break;
                }
                case "del":{
                    List<String> ids = Arrays.asList(String.valueOf(vo.getId()).split(","));
                    for (String id:ids){
                        DRoleEntity role = dao.findById(DRoleEntity.class,Integer.parseInt(id));
                        role.setIsDel(0);
                        role.setDelTime(System.currentTimeMillis());
                        dao.update(role);
                    }
                    break;
                }
                case "add":{
                    DRoleEntity role = new DRoleEntity();
                    role.setName(vo.getName());
                    role.setDis(vo.getDis());
                    role.setIsDel(1);
                    dao.save(role);
                    break;
                }
                default:{
                    break;
                }
            }
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();

        }
        return new Gson().toJson(result);
    }
    /**
     * 获取权限列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRoleDetailByRoleId", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    public String getRoleDetailByRoleId(RoleVO vo) throws Exception{
        BaseResult result = new BaseResult();
        try {
            DRoleEntity role =dao.findById(DRoleEntity.class,Integer.parseInt(String.valueOf(vo.getId())));
            List<DMenuEntity> allMenuList =dao.findAll("from d_menu  order by id asc");
            List<String> scopeList = new ArrayList<>();
            if(null!=role.getScope()&&!role.getScope().equals("")){
                 scopeList = Arrays.asList(role.getScope().split(","));
            }
            HashMap<Integer,Drawer> roleMap = new HashMap<>();
            Drawer drawer ;
            Folder folder = new Folder();
            Item item;
                for (DMenuEntity menu:allMenuList){
                        if(null==menu.getMethod()&&null==menu.getpId()){
                            drawer = new Drawer();
                            drawer.setName(menu.getName());
                            if(scopeList.contains(String.valueOf(menu.getId()))){
                                drawer.setIsSelect(true);
                            }
                            drawer.setId(menu.getId());
                            roleMap.put(menu.getId(),drawer);
                        }else if(null==menu.getMethod()&&null!=menu.getpId()){
                            folder = new Folder();
                            drawer =roleMap.get(menu.getpId());
                            folder.setName(menu.getName());
                            folder.setId(menu.getId());
                            if(scopeList.contains(String.valueOf(folder.getId()))){
                                folder.setIsSelect(true);
                            }
                            drawer.getFolders().add(folder);
                        }else if(null!=menu.getMethod()&&null!=menu.getpId()){
                            item = new Item();
                            item.setId(menu.getId());
                            item.setName(menu.getName());
                            if(scopeList.contains(String.valueOf(item.getId()))){
                                item.setIsSelect(true);
                            }
                            folder.getItems().add(item);
                        }
                }
                result.setBizObject(roleMap);
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

    /**
     * 获取权限列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveRoleScope", produces = "text/json;charset=UTF-8", method = RequestMethod.POST)
    public String saveRoleScope(RoleVO vo) throws Exception{
        BaseResult result = new BaseResult();
        try {
            DRoleEntity role =dao.findById(DRoleEntity.class,Integer.parseInt(String.valueOf(vo.getId())));
            role.setScope(vo.getScope());
            dao.update(role);
        }catch (Exception e){

            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }
}




