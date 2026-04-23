<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <%-- メインコンテンツ --%>
    <c:param name="content">
        <section class="mb-3">
            <h2 class="h4 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目削除</h2>
            <hr class="mt-0">

            <p class="px-2">「${subject.name}(${subject.cd})」を科目を削除してもよろしいですか？</p>

            <%-- 操作ボタン --%>
            <div class="mt-4">
                <form action="SubjectDeleteExecute.action" method="post">
                    <input type="hidden" name="cd" value="${subject.cd}">
                    <button class="btn btn-danger me-2" type="submit">削除</button>
                    <a href="SubjectList.action" class="btn btn-outline-secondary">戻る</a>
                </form>
            </div>
        </section>
    </c:param>
</c:import>
