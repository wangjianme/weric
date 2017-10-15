package cn.sms.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImgServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1:声明图片大小
		int width = 60;
		int height = 30;
		// 2:声明内存中的图片
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("ST", Font.BOLD, 18));
		Random r = new Random();
		String code = "";
		for (int i = 0; i < 4; i++) {
			g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
			int a = r.nextInt(10);
			code += a;
			g.drawString("" + a, i * 15, 10 + r.nextInt(20));
		}
		// 放到Session中
		req.getSession().setAttribute("code", code);

		for (int i = 0; i < 10; i++) {
			g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
			g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
		}

		g.dispose();

		resp.setContentType("image/jpeg");
		ImageIO.write(img, "JPEG", resp.getOutputStream());

	}
}
