<%-- 科目登録完了JSP (SBJM003) --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目登録完了</h2>

            <div class="mx-4 mt-4">
                <p>科目情報を登録しました。</p>

                <div class="mt-3">
                    <a href="SubjectCreate.action">戻る（続けて登録する）</a>
                </div>
                <div class="mt-2">
                    <a href="SubjectList.action">科目一覧へ</a>
                </div>
            </div>
        </section>
    </c:param>
</c:import>
