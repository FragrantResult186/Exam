<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	<c:param name="content">
    	<p class="alert alert-success">登録が完了しました</p>
    	<br><br><br><br><br><br>
    	<a href="StudentList.action" style="margin-right: 70px;">戻る</a>
    	<a href="StudentList.action">学生一覧</a>
	</c:param>
</c:import>