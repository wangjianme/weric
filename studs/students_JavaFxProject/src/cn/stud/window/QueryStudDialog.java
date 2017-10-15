package cn.stud.window;

import java.util.List;

import cn.stud.db.DataBase;
import cn.stud.domain.Stud;
import cn.stud.window.StudWindow.Back;
import cn.stud.window.StudWindow.QueryBack;
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
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class QueryStudDialog extends Stage {
	QueryBack back;

	public QueryStudDialog(QueryBack back) {
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

		TextField tfAge1 = new TextField();
		TextField tfAge2 = new TextField();
		tfAge1.setPrefWidth(50);
		tfAge2.setPrefWidth(50);
		
		HBox ageBox = new HBox(5);
		Label ageLabel = new Label("~");
	    ageBox.getChildren().addAll(tfAge1,ageLabel,tfAge2);
	    HBox.setHgrow(ageLabel, Priority.ALWAYS);
	    
	    
	    
	    p.add(ageBox, 1, 1);

		p.add(new Label("学生性别："), 0, 2);
		///
		RadioButton rbSex1 = new RadioButton("男");
		rbSex1.setSelected(true);// 设置为默认的选择
		RadioButton rbSex2 = new RadioButton("女");
		RadioButton rbSex3 = new RadioButton("不限");
		HBox rbBox = new HBox(5, rbSex1, rbSex2,rbSex3);
		p.add(rbBox, 1, 2);
		ToggleGroup group = new ToggleGroup();
		rbSex1.setToggleGroup(group);
		rbSex2.setToggleGroup(group);
		rbSex3.setToggleGroup(group);
		////

		HBox box = new HBox(10);
		box.setAlignment(Pos.CENTER);
		Button btnSave = new Button("查询");
		Button btnCancel = new Button("关闭");
		box.getChildren().addAll(btnSave, btnCancel);

		p.add(box, 1, 3);

		Scene sc = new Scene(p, 300, 350);
		setScene(sc);
		show();
		// 保存
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = tfName.getText();//null , ""
				String age1 = tfAge1.getText();
				String age2 = tfAge2.getText();
				String sex = "不限";
				if(rbSex1.isSelected()){
					sex = "男";
				}else if(rbSex2.isSelected()){
					sex = "女";
				}
				Stud stud = new Stud();
				stud.setName(name);
				stud.setAge1(age1.equals("")?null:Integer.parseInt(age1));
				stud.setAge2(age2.equals("")?null:Integer.parseInt(age2));
				stud.setSex(sex);
				//获取查询的结果
				List<Stud> list = DataBase.getInstace().queryWithStud(stud);
				back.sendBack(list);
				close();
				
			}
		});
	}
}
