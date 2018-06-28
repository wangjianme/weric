package com.topsen.financial.util.support.id;

public class CodeCreater {
	private static final CodeCreater creater = new CodeCreater();
	private CodeCreater(){}
	public static CodeCreater getInstance(){
		return creater;
	}
	
	public long createIdBySysTime(){
		long l = 0;
		synchronized(this){
			l = System.currentTimeMillis();
		}
		
		return l;
	}
	public String createIdByRandomChar(boolean upper,boolean number){
		StringBuffer sb = new StringBuffer();
		int baseCode = 97;
		int step = 26;
		if (upper && !number){
			baseCode = 65;
		}else if (number){
			baseCode = 48;
			step=10;
		}
		for (int i = 0;i < 4;i++){
			int code = (int)(Math.random()*step+baseCode);
			sb.append((char)code);
		}
		return sb.toString();
	}
	
	public String createIdByRandomNumber(){
		return this.createIdByRandomChar(false,true);
	}
	public String createCodeByRandomNumberAndBaseString(String baseString){
		return baseString.trim()+this.createIdByRandomNumber();
	}
	public String createIdByRandomChar(){
		return createIdByRandomChar(false,false);
	}
	public String createCodeByRandomCharAndBaseString(String baseString){
		return this.createCodeByRandomCharAndBaseString(baseString,false);
	}
	public String createCodeByRandomCharAndBaseString(String baseString,boolean upper){
		return baseString.trim()+this.createIdByRandomChar(upper,false);
	}
	public String createIdByCharAndTime(){
		long l = this.createIdBySysTime();
		String randomChar = this.createIdByRandomChar();
		return l+randomChar;
	}
	public static void main(String[] args){
		System.out.println(CodeCreater.getInstance().createCodeByRandomNumberAndBaseString("003"));
	}
}
