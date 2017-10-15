package com.myexam.stud.detail.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import com.myexam.domain.Stud;
import com.myexam.pub.JSONUtil;
import com.myexam.stud.detail.service.IStudDetailService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 学生明细信息
 * 
 * @author wangjianme
 * @version 1.0,2010-12-9
 */
@SuppressWarnings("serial")
public class StudDetailAction extends ActionSupport{
	private IStudDetailService service;

	public IStudDetailService getService() {
		return service;
	}

	public void setService(IStudDetailService service) {
		this.service = service;
	}

	public String execute() throws Exception {
		return SUCCESS;
	}

	/**
	 * 上传图片
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

	public String upload() throws Exception {
		String[] allow = { "image/png", "image/jpeg", "application/x-bmp",
				"image/pjpeg" };
		List<String> allows = Arrays.asList(allow);
		Map<String, Object> result = new HashMap<String, Object>();
		if (allows.indexOf(getFileContentType()) == -1) {
			result.put("img", false); // 不支持的文档类型
		} else {
			Stud stud = (Stud) ActionContext.getContext().getSession().get(
					"stud");
			String oldImg = stud.getStudPic(); // 老图的名称
			String path = ServletActionContext.getServletContext().getRealPath(
					"/images");
			InputStream in = new FileInputStream(getFile());
			byte[] b = new byte[1024 * 4];
			int len = 0;
			OutputStream out = new FileOutputStream(path + "/" + oldImg);
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.close();
			in.close();
			result.put("img", true);
		}
		result.put("success", true);
		String json = JSONUtil.toJsonString(result);
		ActionContext.getContext().put("jsonStr", json);
		return "jsonPage";
	}

	private String json;
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}

	public String save() throws Exception {
		System.err.println("Save method.....>>:"+getJson());
		Map<String,Object> param = JSONUtil.getMap(getJson());
		System.err.println("param:"+param);
		Stud stud = (Stud)ActionContext.getContext().getSession().get("stud");
		param.put("stud",stud);
		param = getService().update(param);
		System.err.println("返回的>>"+param);
		if(param.get("valid").equals(true)){
			stud = (Stud)param.get("stud");
			ActionContext.getContext().getSession().put("stud",stud);
			param.remove("stud");
		}
		String jsonStr = JSONUtil.toJsonString(param);
		ActionContext.getContext().put("jsonStr",jsonStr);
		return "jsonPage";
	}
}
