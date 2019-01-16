package com.faurecia.controller;

import com.faurecia.dao.BaseDao;
import com.faurecia.model.BLaunchFileEntity;
import com.faurecia.model.BLaunchProgramEntity;
import com.faurecia.model.BLaunchProgramPicEntity;
import com.faurecia.model.DLaunchTypeEntity;
import com.faurecia.vo.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */
@Controller(value="/launch")
public class LauchController {

    @Autowired
    private BaseDao dao;
    private final static Logger INFO_LOG = LoggerFactory.getLogger("infoLog");
    private final static Logger ERROR_LOG = LoggerFactory.getLogger("errorLog");





    /**
     * pdf类型分页查询
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getLaunchTypeList",produces = "text/json;charset=UTF-8",method = RequestMethod.POST )
    public  String getLaunchTypeList(BaseArg vo) throws Exception{
        BaseResult result = new BaseResult();
        try {
            String sqlStr = "from d_launch_type";
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

    /**
     * pdf类型 供页面下拉框使用
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getAllLaunchTypeList",produces = "text/json;charset=UTF-8",method = RequestMethod.POST )
    public  String getAllLaunchTypeList(BaseArg vo) throws Exception{
        BaseResult result = new BaseResult();
        try {
            String sqlStr = "from d_launch_type  order by id desc";
            List<DLaunchTypeEntity> list =dao.findAll(sqlStr);
            HashMap<Integer,String> res = new HashMap<>();
            for (DLaunchTypeEntity type:list){
                res.put(type.getId(),type.getName());
            }
            result.setBizObject(res);
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }


    /**
     * 获取所有项目 供页面下拉框使用
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getAllLaunchProgramList",produces = "text/json;charset=UTF-8" )
    public  String getAllLaunchProgramList(BaseArg vo)throws Exception{
        BaseResult result = new BaseResult();
        try {
            String sqlStr = "from b_launch_program where 1=1 and isDel=1 order by id desc";
            List<BLaunchProgramEntity> list =dao.findAll(sqlStr);
            HashMap<Integer,String> res = new HashMap<>();
            for (BLaunchProgramEntity program:list){
                res.put(program.getId(),program.getName());
            }
            result.setBizObject(res);
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

    /**
     * 获取所有项目 供按条件查询时 下拉框使用
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getAllLaunchProgramList4select",produces = "text/json;charset=UTF-8" )
    public  String getAllLaunchProgramList4select(BaseArg vo)throws Exception{
        StringBuffer sb = new StringBuffer("<select>");
        sb.append("<option value=''>全部</option>");
        try {
            String sqlStr = "from b_launch_program where 1=1 and isDel=1  order by id desc";
            List<BLaunchProgramEntity> list =dao.findAll(sqlStr);
            for (BLaunchProgramEntity program:list){
                sb.append("<option value='" + program.getId() + "'>" + program.getName() + "</option>");
            }
            sb.append("</select>");
        }catch (Exception e){
            throw new Exception();
        }
        return sb.toString();
    }

    /**
     * 获取所有pdf类型 供按条件查询时 下拉框使用
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getAllLaunchTypeList4select",produces = "text/json;charset=UTF-8" )
    public  String getAllLaunchTypeList4select(BaseArg vo) throws Exception{
        StringBuffer sb = new StringBuffer("<select>");
        sb.append("<option value=''>全部</option>");
        try {
            String sqlStr = "from d_launch_type  order by id desc";
            List<DLaunchTypeEntity> list =dao.findAll(sqlStr);
            for (DLaunchTypeEntity program:list){
                sb.append("<option value='" + program.getId() + "'>" + program.getName() + "</option>");
            }
            sb.append("</select>");
        }catch (Exception e){
            throw new Exception();
        }
        return sb.toString();
    }

    /**
     * 是否展示 供按条件查询时 下拉框使用
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getIsDisplay4select",produces = "text/json;charset=UTF-8" )
    public  String getIsDisplay4select(BaseArg vo) throws Exception{
        StringBuffer sb = new StringBuffer("<select>");
        sb.append("<option value=''>全部</option>");
        sb.append("<option value='0'>展示</option>");
        sb.append("<option value='1'>不展示</option>");
        sb.append("</select>");
        return sb.toString();
    }

    /**
     * 上传pdf
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/uploadPdf",produces = "application/json;charset=UTF-8")
    public String uploadPdf(@RequestParam("fileUrl") MultipartFile file ,HttpSession session)throws Exception{
        BaseResult result = new BaseResult();
        try {
            if(((CommonsMultipartFile) file).getSize()/1024>50000){
                result.setStatus(2);
                result.setMessage("上传文件过大！");
                return  new Gson().toJson(result);
            }
            String fileName = ((CommonsMultipartFile) file).getFileItem().getName();
            String nameSuffix2=fileName.substring(fileName.lastIndexOf(".")+1);
//            if(!nameSuffix2.toLowerCase().equals("pdf")){
//                result.setStatus(2);
//                result.setMessage("只能上传pdf文件！");
//                return  new Gson().toJson(result);
//            }
            String baseUrl = session.getServletContext().getRealPath("/")+"pages\\Launch_pdf_local";
            fileName=fileName.substring(0,fileName.lastIndexOf("."))+"-"+System.currentTimeMillis()+"."+nameSuffix2;
            File disFile = new File(baseUrl+"\\"+fileName);
            File fileParent = disFile.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            file.transferTo(disFile);
            for(SocketController item: SocketController.webSocketSet){
                if(item.getHttpSession().getAttribute("userName").equals("launch")){
                    item.sendMessage("update");
                }
            }
            result.setMessage(fileName);

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
        return new Gson().toJson(result);

    }

    @ResponseBody
    @RequestMapping(value="/coverPic",produces = "application/json;charset=UTF-8")
    public String coverPic(@RequestParam("fileUrl") MultipartFile file ,String programId,HttpSession session)throws Exception{
        BaseResult result = new BaseResult();
        try {
            if(((CommonsMultipartFile) file).getSize()/1024>50000){
                result.setStatus(2);
                result.setMessage("上传文件过大！");
                return  new Gson().toJson(result);
            }
            String fileName = ((CommonsMultipartFile) file).getFileItem().getName();
            String nameSuffix2=fileName.substring(fileName.lastIndexOf(".")+1);
            if(!nameSuffix2.toLowerCase().equals("jpg")&&!nameSuffix2.toLowerCase().equals("png")&&!nameSuffix2.toLowerCase().equals("bmp")){
                result.setStatus(2);
                result.setMessage("只能上传jpg、png、bmp文件！");
                return  new Gson().toJson(result);
            }

            String baseUrl = session.getServletContext().getRealPath("/")+"pages\\Launch_pic_local";

            File disFile = new File(baseUrl+"\\"+fileName);
            File fileParent = disFile.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            file.transferTo(disFile);
            BLaunchProgramPicEntity pic = new BLaunchProgramPicEntity();
            pic.setPicUrl(fileName);
            pic.setProgramId(Integer.valueOf(programId));
            dao.save(pic);
            pic.setPicUrl("http://"+ InetAddress.getLocalHost().getHostAddress()+":8080/faurecia-launch/pages/Launch_pic_local/"+fileName);
            result.setBizObject(pic);
            for(SocketController item: SocketController.webSocketSet){
                if(item.getHttpSession().getAttribute("userName").equals("launch")){
                    item.sendMessage("update");
                }
            }
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);

    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/uploadPic",produces = "application/json;charset=UTF-8")
    public String uploadPic(@RequestParam("fileUrl") MultipartFile file ,String id,HttpSession session)throws Exception{
        BaseResult result = new BaseResult();
        try {
            if(((CommonsMultipartFile) file).getSize()/1024>50000){
                result.setStatus(2);
                result.setMessage("上传文件过大！");
                return  new Gson().toJson(result);
            }
            String fileName = ((CommonsMultipartFile) file).getFileItem().getName().replaceAll(" ","");
            String nameSuffix2=fileName.substring(fileName.lastIndexOf(".")+1);
            if(!nameSuffix2.toLowerCase().equals("jpg")&&!nameSuffix2.toLowerCase().equals("png")&&!nameSuffix2.toLowerCase().equals("bmp")){
                result.setStatus(2);
                result.setMessage("只能上传jpg、png、bmp文件！");
                return  new Gson().toJson(result);
            }
            List list =dao.findAll("from b_launch_program_pic where picUrl='"+fileName+"'");
            if(list.size()>0){
                result.setBizObject(list.get(0));
                result.setStatus(3);
                result.setMessage("系统内已有同名文件！");
                return  new Gson().toJson(result);
            }
            String baseUrl = session.getServletContext().getRealPath("/")+"pages\\Launch_pic_local";

            File disFile = new File(baseUrl+"\\"+fileName);
            File fileParent = disFile.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            file.transferTo(disFile);
            BLaunchProgramPicEntity pic = new BLaunchProgramPicEntity();
            pic.setPicUrl(fileName);
            pic.setProgramId(Integer.valueOf(id));
            dao.save(pic);
            pic.setPicUrl("http://"+ InetAddress.getLocalHost().getHostAddress()+":8080/faurecia-launch/pages/Launch_pic_local/"+fileName);
            result.setBizObject(pic);
//            for(SocketController item: SocketController.webSocketSet){
//                if(item.getHttpSession().getAttribute("userName").equals("launch")){
//                    item.sendMessage("update");
//                }
//            }
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);

    }

    /**
     * 删除项目图片
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/delPic",produces = "text/json;charset=UTF-8",method = RequestMethod.POST )
    public  String delPic(LaunchVO vo)throws Exception{
        BaseResult result = new BaseResult();
        try{
            dao.del(BLaunchProgramPicEntity.class,Integer.parseInt(String.valueOf(vo.getId())));
            for(SocketController item: SocketController.webSocketSet){
                if(item.getHttpSession().getAttribute("userName").equals("launch")){
                    item.sendMessage("update");
                }
            }
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }


    /**
     * launch类型增删改
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/launchTypeOption",produces = "text/json;charset=UTF-8",method = RequestMethod.POST )
    public  String launchTypeOption(LaunchVO vo)throws Exception{
        BaseResult result = new BaseResult();
        try {
            switch (vo.getOper()){
                case "edit": {
                    DLaunchTypeEntity type = dao.findById(DLaunchTypeEntity.class,Integer.parseInt(String.valueOf(vo.getId())));
                    type.setIndexNo(vo.getIndexNo());
                    type.setName(vo.getName());
                    dao.update(type);
                    break;
                }
                case "del":{
                    List<String> ids = Arrays.asList(String.valueOf(vo.getId()).split(","));
                    for (String id:ids){
                        dao.del(DLaunchTypeEntity.class,Integer.parseInt(id));
                    }
                    break;
                }
                case "add":{
                    DLaunchTypeEntity type = new DLaunchTypeEntity();
                    type.setName(vo.getName());
                    type.setIndexNo(vo.getIndexNo());
                    dao.save(type);
                    break;
                }
                default:{
                    break;
                }
            }
            for(SocketController item: SocketController.webSocketSet){
                if(item.getHttpSession().getAttribute("userName").equals("launch")){
                    item.sendMessage("update");
                }
            }
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }



    /**
     * 后台管理系统分页获取launch列表
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getLaunchList",produces = "text/json;charset=UTF-8",method = RequestMethod.POST )
    public  String getLaunchList(BaseArg vo)throws Exception{
        BaseResult result = new BaseResult();
        try {
            result.setRows(dao.findByParamAndPage(BLaunchFileEntity.class,vo));
            result.setRecords(dao.countByParamAndPage(BLaunchFileEntity.class,vo));
            result.setTotal(result.getRecords()/vo.getRows()+(result.getRecords()%vo.getRows()>0?1:0));
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }
    /**
     * 根据id查看pdf放大演示
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getPdfById",produces = "text/json;charset=UTF-8",method = RequestMethod.POST )
    public  String getPdfById(LaunchVO vo) throws Exception{
        BaseResult result = new BaseResult();
        try {
            BLaunchFileEntity file = dao.findById(BLaunchFileEntity.class,Integer.parseInt(String.valueOf(vo.getId())));
            file.setFileUrl("http://"+ InetAddress.getLocalHost().getHostAddress()+":8080/faurecia-launch/pages/Launch_pdf_local/"+file.getFileUrl());
            result.setBizObject(file);

        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

    /**
     * launch文件增删改
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/launchListOption",produces = "application/json;charset=UTF-8",method = RequestMethod.POST )
    public  String launchListOption(LaunchVO vo)throws Exception{
        BaseResult result = new BaseResult();

        try {
            switch (vo.getOper()){
                case "edit": {
                    BLaunchFileEntity type = dao.findById(BLaunchFileEntity.class,Integer.parseInt(String.valueOf(vo.getId())));
                    type.setName(vo.getName());
                    type.setDis(vo.getDis());
                    type.setIsDisplay(vo.getIsDisplay());
                    if(null!=vo.getTempFileUrl()){
                        type.setFileUrl(vo.getTempFileUrl());
                    }
                    type.setFileType(vo.getFileType());
                    type.setLaunchType(vo.getLaunchType());
                    type.setProgramId(vo.getProgramId());
                    dao.update(type);
                    break;
                }
                case "del":{
                    List<String> ids = Arrays.asList(String.valueOf(vo.getId()).split(","));
                    for (String id:ids){
                        BLaunchFileEntity type = dao.findById(BLaunchFileEntity.class,Integer.parseInt(id));
                        type.setIsDel("0");
                        type.setDelTime(System.currentTimeMillis());
                        dao.update(type);
                    }
                    break;
                }
                case "add":{
                    BLaunchFileEntity type = new BLaunchFileEntity();
                    type.setName(vo.getName());
                    type.setDis(vo.getDis());
                    type.setIsDisplay(vo.getIsDisplay());
                    if(null!=vo.getTempFileUrl()){
                        type.setFileUrl(vo.getTempFileUrl());
                    }
                    type.setFileType(vo.getFileType());
                    type.setIsDel("1");
                    type.setLaunchType(vo.getLaunchType());
                    type.setProgramId(vo.getProgramId());
                    type.setCreateTime(System.currentTimeMillis());
                    dao.save(type);
                    break;
                }
                default:{
                    break;
                }
            }
            for(SocketController item: SocketController.webSocketSet){
                if(item.getHttpSession().getAttribute("userName").equals("launch")){
                    item.sendMessage("update");
                }
            }
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

    /**
     * 后台管理系统分页获取launch项目列表
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getProgramPicsById",produces = "text/json;charset=UTF-8",method = RequestMethod.POST )
    public  String getProgramPicsById(LaunchVO vo)throws Exception{
        BaseResult result = new BaseResult();
        try{
            List<BLaunchProgramPicEntity> pics =dao.findAll("from b_launch_program_pic where programId="+vo.getId());
            for (BLaunchProgramPicEntity pic:pics){
                pic.setPicUrl("http://"+ InetAddress.getLocalHost().getHostAddress()+":8080/faurecia-launch/pages/Launch_pic_local/"+pic.getPicUrl());
            }
            result.setBizObject(pics);
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return  new Gson().toJson(result);
    }

    /**
     * 后台管理系统分页获取launch项目列表
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getProgramList4System",produces = "text/json;charset=UTF-8",method = RequestMethod.POST )
    public  String getProgramList4System(BaseArg vo)throws Exception{
        BaseResult result = new BaseResult();
        try {
            String sqlStr = "from b_launch_program where 1=1 and isDel=1 ";
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

    /**
     * launch项目增删改
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/programListOption",produces = "application/json;charset=UTF-8",method = RequestMethod.POST )
    public  String programListOption(LaunchVO vo) throws Exception{
        BaseResult result = new BaseResult();

        try {
            switch (vo.getOper()){
                case "edit": {
                    BLaunchProgramEntity program = dao.findById(BLaunchProgramEntity.class,Integer.parseInt(String.valueOf(vo.getId())));
                    program.setName(vo.getName());
                    program.setIsDisplay(vo.getIsDisplay());
                    program.setIndexNo(vo.getIndexNo());
                    dao.update(program);
                    break;
                }
                case "del":{
                    List<String> ids = Arrays.asList(String.valueOf(vo.getId()).split(","));
                    for (String id:ids){
                        BLaunchProgramEntity program = dao.findById(BLaunchProgramEntity.class,Integer.parseInt(id));
                        program.setIsDel("0");
                        program.setDelTime(System.currentTimeMillis());
                        dao.update(program);
                    }
                    break;
                }
                case "add":{
                    BLaunchProgramEntity program = new BLaunchProgramEntity();
                    program.setName(vo.getName());
                    program.setIsDisplay(vo.getIsDisplay());
                    program.setIndexNo(vo.getIndexNo());
                    program.setIsDel("1");
                    program.setCreateTime(System.currentTimeMillis());
                    dao.save(program);
                    break;
                }
                default:{
                    break;
                }
            }
            for(SocketController item: SocketController.webSocketSet){
                if(item.getHttpSession().getAttribute("userName").equals("launch")){
                    item.sendMessage("update");
                }
            }
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

    /**
     * launch 看板获取pdf信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getPdfList",produces = "application/json;charset=UTF-8",method = RequestMethod.POST )
    public  String getPdfList(LaunchVO vo,HttpSession session)throws Exception{
        BaseResult result = new BaseResult();
        session.setAttribute("userName","launch");
        session.setAttribute("adminId",null);
        session.setAttribute("role","");
        try {
            List list =dao.findAll(" select b.name,b.dis,b.fileUrl,b.id ,d.name as dname  from d_launch_type d " +
                             " left join b_launch_file b on d.id=b.launchType " +
                             " where b.isDel=1 and b.isDisplay=0 and b.programId="+vo.getProgramId()+" order by d.indexNo asc");
            List<LaunchVO> voList = new ArrayList<>();
            LaunchVO launchvo ;
            for (Object o :list){
                Object[] launch = (Object[])o;
                launchvo = new LaunchVO();
                launchvo.setName(String.valueOf(launch[0]));
                launchvo.setDis(String.valueOf(launch[1]));
                launchvo.setId(String.valueOf(launch[3]));
                launchvo.setLaunchType(String.valueOf(launch[4]));
                launchvo.setFileUrl("http://"+ InetAddress.getLocalHost().getHostAddress()+":8080/faurecia-launch/pages/Launch_pdf_local/"+String.valueOf(launch[2]));
                voList.add(launchvo);
            }
            result.setBizObject(voList);
        }catch (Exception e){
            result.setStatus(-1);
            throw new Exception();
        }
        return new Gson().toJson(result);
    }

    /**
     * launch 看板获取项目信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getProgramList",produces = "application/json;charset=UTF-8",method = RequestMethod.POST )
    public  String getProgramList(HttpSession session) throws Exception{
        BaseResult result = new BaseResult();
        session.setAttribute("userName","launch");
        session.setAttribute("adminId",null);
        session.setAttribute("role","");
        try {
            List<BLaunchProgramEntity> programList =dao.findAll(" from b_launch_program p where p.isDel=1 AND p.isDisplay=0 order by p.indexNo asc ");
            for (BLaunchProgramEntity programEntity:programList){
                List<BLaunchProgramPicEntity> pics =dao.findAll("from b_launch_program_pic where programId="+programEntity.getId());
                for (BLaunchProgramPicEntity pic:pics){
                    pic.setPicUrl("http://"+ InetAddress.getLocalHost().getHostAddress()+":8080/faurecia-launch/pages/Launch_pic_local/"+pic.getPicUrl());
                }
                programEntity.setPiclist(pics);
            }
            result.setBizObject(programList);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
        return new Gson().toJson(result);
    }
}
