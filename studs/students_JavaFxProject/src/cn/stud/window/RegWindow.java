package cn.stud.window;

import java.text.SimpleDateFormat;
import java.util.Date;

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

public class RegWindow {
	public RegWindow(Stage stage) {
		stage.setTitle("请注册");
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

		p.add(new Label("确认密码："), 0, 2);

		TextField tfPwd2 = new PasswordField();
		p.add(tfPwd2, 1, 2);

		HBox box = new HBox(10);
		box.setAlignment(Pos.CENTER);
		Button btnLogin = new Button("返回");

		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new LoginWindow().start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Button btnReg = new Button("保存");
		box.getChildren().addAll(btnLogin, btnReg);

		btnReg.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = tfName.getText();
				String pwd = tfPwd.getText();
				// 判断
				System.err.println("na eis:" + name);
				boolean boo = DataBase.getInstace().validateName(name);
				if (boo) {
					Alert alert = new Alert(AlertType.ERROR, "用户名已经被占用", ButtonType.YES);
					alert.show();
				} else {
					User user = new User(name, pwd);
					DataBase.getInstace().reg(user);
					Alert alert = new Alert(AlertType.INFORMATION, "注册成功", ButtonType.YES);
					alert.showAndWait();
					try {
						new LoginWindow().start(stage);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

		p.add(box, 1, 3);
		Scene sc = new Scene(p, 300, 300);
		stage.setScene(sc);
		stage.show();
	}
}
