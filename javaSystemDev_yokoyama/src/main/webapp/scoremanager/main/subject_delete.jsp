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

            <p class="px-2">以下の科目を削除します。よろしいですか？</p>

            <%-- 科目情報の表示 --%>
            <div class="mb-3">
                <label class="form-label">科目コード</label>
                <p class="form-control-plaintext fw-bold ps-2">${subject.cd}</p>
            </div>
            <div class="mb-3">
                <label class="form-label">科目名</label>
                <p class="form-control-plaintext fw-bold ps-2">${subject.name}</p>
            </div>

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
