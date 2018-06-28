<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.oratry.makecertpic.*" %>
<%
MakeCertPic image=new MakeCertPic();//构建产生验证码库的对象  为了产生验证码做准备
String str=image.getCertPic(0,0,response.getOutputStream());//调用产生验证码的方法产生验证码
session.setAttribute("certCode",str);//将产生的验证码利用session存起来   为了和输入的验证码的做比较
out.clear();//清空缓存
out = pageContext.pushBody();//清空缓存
%>