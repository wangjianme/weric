package com.myexam.teac.exam.ques.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.myexam.pub.JSONUtil;
import com.myexam.pub.MyUUID;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 试题上传图片的类
 * @author wangjianme
 * @version 1.0,2010-10-26
 */
@SuppressWarnings("serial")
public class UploadQuesPicAction extends ActionSupport{
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
	public String execute() throws Exception{
		System.err.println("fileName:"+getFileFileName());
		System.err.println("type:"+getFileContentType());
		Map<String,Object> result = new HashMap<String, Object>();
		List<String> allow = new ArrayList<String>();
		allow.add("image/jpeg");
		allow.add("image/pjpeg");
		allow.add("application/x-bmp");
		allow.add("image/png");
		if(allow.contains(getFileContentType())){//开始上传
			result.put("allow",true);
			String fileName = getFileFileName();
			String extName  = fileName.substring(fileName.lastIndexOf("."),fileName.length());
			String uuid = MyUUID.getUUID();
			String path = ServletActionContext.getServletContext().getRealPath("/images/ques/");
			fileName = path+"/"+uuid+extName;
			System.err.println("fileName:"+fileName);
			InputStream in = new FileInputStream(getFile());
			OutputStream out = new FileOutputStream(fileName);
			byte[] b = new byte[2048];
			int len = 0;
			while((len=in.read(b))!=-1){
				out.write(b,0,len);
			}
			out.close();
			in.close();
			result.put("fileName",uuid+extName);
		}else{
			result.put("allow",false);
		}
		result.put("success",true);
		String json = JSONUtil.toJsonString(result);
		System.err.println("json:"+json);
		ActionContext.getContext().put("jsonStr",json);
		return "jsonPage";
	}
}
