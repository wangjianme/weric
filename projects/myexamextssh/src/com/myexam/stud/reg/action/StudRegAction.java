package com.myexam.stud.reg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.myexam.domain.Stud;
import com.myexam.pub.JSONUtil;
import com.myexam.pub.Md5Password;
import com.myexam.pub.MyUUID;
import com.myexam.stud.reg.service.IStudRegService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 学生注册功能
 * @author wangjianme
 * @version 1.0,2010-10-18
 */
@SuppressWarnings("serial")
public class StudRegAction extends ActionSupport implements ModelDriven<Stud>{
	
	private IStudRegService service;

	public IStudRegService getService() {
		return service;
	}

	public void setService(IStudRegService service) {
		this.service = service;
	}
	public String execute(){
		return SUCCESS;
	}
	/**
	 * 查询学制
	 */
	public String queryEdusys(){
		Map<String,Object> mm = getService().queryEdusys();
		String jsonStr = JSONUtil.toJsonString(mm);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 查询专业
	 */
	public String queryMajor(){
		Map<String,Object> res = getService().queryMajor(getEdusysId());
		String jsonStr = JSONUtil.toJsonString(res);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 查询班级
	 */
	private String edusysId;
	private String majorId;
	private String clsName;
	public String getEdusysId() {
		return edusysId;
	}

	public void setEdusysId(String edusysId) {
		this.edusysId = edusysId;
	}

	public String getMajorId() {
		return majorId;
	}

	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}
	public String queryCls(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("edusysId", getEdusysId());
		param.put("majorId",getMajorId());
		param.put("clsName", getClsName());
		Map<String,Object> result = getService().queryCls(param);
		String jsonString = JSONUtil.toJsonString(result);
		System.err.println(">>>:"+jsonString);
		ActionContext.getContext().put("jsonStr",jsonString);
		return "jsonPage";
	}
	/**
	 * 保存方法
	 */
	private File file;
	private String fileFileName;
	private String fileContentType;
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String save() throws Exception{ 
		Map<String,Object> result = new HashMap<String, Object>();
		System.err.println(">>>>>>:"+JSONObject.fromObject(stud).toString());
		System.err.println(">>>:"+getFile());
		System.err.println(">>>>:"+getFileFileName());
		System.err.println(">>>:"+getFileContentType());
		String[] allow = {"image/png","image/jpeg","application/x-bmp","image/pjpeg"};
		List<String> allows= Arrays.asList(allow);
		if(allows.indexOf(getFileContentType())==-1){
			System.err.println("不支持的文件类型.....");
			result.put("success", true);
			result.put("img",false);
		}else{
			result.put("img", true);//允许的文件类型
			String fileName = getFileFileName();
			fileName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
			System.err.println("fileName:"+fileName);
			String uuid = MyUUID.getUUID();
			fileName = uuid+fileName;
			System.err.println("fileName:"+fileName);
			stud.setStudPic(fileName);
			String path = ServletActionContext.getServletContext().getRealPath("/images");
			InputStream in = new FileInputStream(getFile());
			OutputStream out = new FileOutputStream(path+"/"+fileName);
			byte[] b = new byte[4096];
			int len = 0;
			while((len=in.read(b))!=-1){
				out.write(b,0,len);
			}
			in.close();
			out.close();
			
			stud.setStudPwd(Md5Password.encodePwd(stud.getStudPwd()));
			stud.setStudAnswer(Md5Password.encodePwd(stud.getStudAnswer()));
			stud.setStudRtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			stud.setStudState("1");
			result = getService().save(stud);
		}
		String jsonStr = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", jsonStr);
		return "jsonPage";
	}
	/**
	 * 模式驱动
	 */
	private Stud stud = new Stud();
	public Stud getModel() {
		return stud;
	}
}
