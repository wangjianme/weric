package cn.grades.core;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 生成验证码
 */
public class ImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1:声明高宽
		int width = 60;
		int height = 30;
		// 2:定义内存中的图片
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 4:获取G
		Graphics g = img.getGraphics();
		// 5:设置背景为white
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("宋体", Font.BOLD, 18));
		// 生成随机数用的对象
		Random r = new Random();
		//---1:声明一个字符串，用于保存四个字母
		String str = "";
		for (int i = 0; i < 4; i++) {
			int a = r.nextInt(10);// 0~9
			//---2:串联字符串
			str+=a;
			g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
			g.drawString("" + a, i * 15, 10 + r.nextInt(20));
		}
		//---3:在生成完成以后，放到session
		HttpSession s = request.getSession();
		s.setAttribute("code",str);
		// 生成线
		for (int i = 0; i <2; i++) {
			g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
			g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
		}

		// 生效
		g.dispose();

		// 设置输出的类型
		response.setContentType("image/jpg");
		// 设置不缓存这个页面
		response.setHeader("expires", "0");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("Progma", "no-cache");

		/*Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 30);*/
		//response.setDateHeader("expires", c.getTimeInMillis());
		// 由于输出图片不是字符，只能使用这字节
		OutputStream out = response.getOutputStream();
		// 使用ImageIO
		ImageIO.write(img, "JPEG", out);
	}
}
