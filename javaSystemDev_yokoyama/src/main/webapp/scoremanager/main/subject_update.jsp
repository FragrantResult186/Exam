<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>
    
    <c:param name="content">
        <section class="mx-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目変更</h2>
            
            <form action="SubjectUpdateExecute.action" method="post">

    科目コード：<input type="text" name="cd" value="${subject.cd}" ><br>
    科目名：<input type="text" name="name" value="${subject.name}"><br>
    <input type="hidden" name="school_cd" value="${subject.schoolCd}">
    

    <input type="submit" value="更新">
</form>
</section>
</c:param>
</c:import>
