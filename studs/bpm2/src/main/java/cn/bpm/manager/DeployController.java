package cn.bpm.manager;

import java.io.File;
import java.io.FileInputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 上传流程定义文件与部署流程定义文件
 * 
 * @author wangjian
 */
@Controller
@RequestMapping(value = "/manager/")
public class DeployController {
	Logger log = Logger.getLogger(DeployController.class);
	/**
	 * 注入流程引擎
	 */
	@Resource(name = "processEngine")
	private ProcessEngine processEngine;
	/**
	 * 或是注入respositoryService<br>
	 * 使用@Required声明是必须要注入的，否则异常
	 */
	private RepositoryService repositoryService;

	@Resource(name = "repositoryService")
	@Required
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	/**
	 * 上传流程定义文件
	 * 
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadBpmnFile(MultipartFile file, String name, HttpServletRequest req) throws Exception {
		// 获取上传的真实目录
		String path = req.getServletContext().getRealPath("/bpmnfiles");
		// 获取文件名称
		String fileName = file.getOriginalFilename();
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		/**
		 * 取消设置新的名称，直接使用原名称，这样，如果有新的上传则直接会覆盖前面的文件，不会造成服务器文件太多的问题
		 * @date 20160430
		 */
		//String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		// 处理文件上传
		file.transferTo(new File(path, fileName));
		// 将name,和fileName放到request中去
		req.setAttribute("name", name);
		req.setAttribute("fileName", fileName);
		// TODO:在上传成功以后，应该判断是否是流程定义文件，使用ZipInputStream读取里面的文件，即可以进行简单的判断
		return "manager/upload_success";
	}

	/**
	 * 部署流程定义文件<br>
	 * 如果没有返回值，默认将返回deploy.jsp文件上去
	 */
	@RequestMapping(value = "/deployment")
	public void deploy(String name, String fileName, HttpServletRequest req) throws Exception {
		String path = req.getServletContext().getRealPath("/bpmnfiles");
		log.info("文件真实目录：" + path);
		// 读取文件
		File file = new File(path,fileName);
		ZipInputStream zip = new ZipInputStream(new FileInputStream(file));
		// 部署文件
		Deployment deployment = repositoryService.createDeployment()//
				.name(name)//
				.addZipInputStream(zip)//
				.deploy();
		log.info("部署完成：" + deployment.getId());
		req.setAttribute("deploymentId", deployment.getId());
		// 查询流程定义id
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()//
				.deploymentId(deployment.getId())//
				.singleResult();
		req.setAttribute("procdefId", processDefinition.getId());
		req.setAttribute("procdefVersion", processDefinition.getVersion());
		req.setAttribute("procdefKey", processDefinition.getKey());
		req.setAttribute("procdefName", processDefinition.getName());
		//部署完成以后，删除流程定义文件
	 	boolean boo =file.delete();
	 	log.info("部署文件删除完成："+boo);
	}

}
