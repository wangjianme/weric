package cn.stud.window;

import cn.stud.domain.Stud;
import cn.stud.window.StudWindow.Back;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddStudDialog extends Stage {
	private Back back;// 接收回调函数的实例

	public AddStudDialog(Back back) {
		this.back = back;
		// 设置成对话框
		initModality(Modality.APPLICATION_MODAL);

		// 添加界面
		GridPane p = new GridPane();
		p.setAlignment(Pos.CENTER);
		p.setVgap(10);
		// 添加元素
		p.add(new Label("学生姓名："), 0, 0);

		TextField tfName = new TextField();
		p.add(tfName, 1, 0);

		p.add(new Label("学生年龄："), 0, 1);

		TextField tfAge = new TextField();
		p.add(tfAge, 1, 1);

		p.add(new Label("学生性别："), 0, 2);
		///
		RadioButton rbSex1 = new RadioButton("男");
		rbSex1.setSelected(true);// 设置为默认的选择
		RadioButton rbSex2 = new RadioButton("女");
		HBox rbBox = new HBox(5, rbSex1, rbSex2);
		p.add(rbBox, 1, 2);
		ToggleGroup group = new ToggleGroup();
		rbSex1.setToggleGroup(group);
		rbSex2.setToggleGroup(group);
		////

		HBox box = new HBox(10);
		box.setAlignment(Pos.CENTER);
		Button btnSave = new Button("保存");
		Button btnCancel = new Button("取消");
		box.getChildren().addAll(btnSave, btnCancel);

		p.add(box, 1, 3);

		Scene sc = new Scene(p, 300, 350);
		setScene(sc);
		show();
		// 保存
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = tfName.getText();
				String age = tfAge.getText();
				age = age.equals("") ? "0" : age;
				Integer _age = Integer.parseInt(age);
				String sex = "男";
				if (rbSex2.isSelected()) {
					sex = "女";
				}
				Stud stud = new Stud();
				stud.setName(name);
				stud.setAge(_age);
				stud.setSex(sex);
				back.sendBack(stud);
				close();// 关闭
			}
		});
	}
}
