package cn.stud.window;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import cn.stud.db.DataBase;
import cn.stud.domain.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sun.applet.Main;

public class LoginWindow extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("请登录");
		GridPane p = new GridPane();
		p.setAlignment(Pos.CENTER);
		p.setVgap(10);
		// 添加元素
		p.add(new Label("姓名："), 0, 0);

		TextField tfName = new TextField();
		p.add(tfName, 1, 0);

		p.add(new Label("密码："), 0, 1);

		TextField tfPwd = new PasswordField();
		p.add(tfPwd, 1, 1);

		HBox box = new HBox(10);
		box.setAlignment(Pos.CENTER);
		Button btnLogin = new Button("登录");

		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = tfName.getText();
				String pwd = tfPwd.getText();
				// 实例化User
				User user = new User(name, pwd);
				boolean boo = DataBase.getInstace().login(user);
				if (boo) {
					System.err.println("登录成功...");
					new StudWindow(stage);
				} else {
					Alert alert = new Alert(AlertType.ERROR, "用户名或密码错误!", ButtonType.YES);
					alert.show();
				}
			}
		});

		Button btnReg = new Button("注册");
		btnReg.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new RegWindow(stage);
			}

		});
		box.getChildren().addAll(btnLogin, btnReg);

		p.add(box, 1, 2);
		Scene sc = new Scene(p, 300, 300);
		stage.setScene(sc);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);// 加载
	}

}
