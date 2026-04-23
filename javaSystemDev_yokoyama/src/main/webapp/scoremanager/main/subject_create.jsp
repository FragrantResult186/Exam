<%-- 科目登録JSP (SBJM002) --%>
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
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目登録</h2>

            <form method="post" action="SubjectCreateExecute.action" class="mx-4">

                <%-- 科目コード --%>
                <div class="mb-3 row">
                    <label class="col-sm-2 col-form-label" for="subject-cd-input">科目コード</label>
                    <div class="col-sm-4">
                        <input class="form-control <c:if test="${!empty errors.get('cd')}">is-invalid</c:if>"
                               type="text" id="subject-cd-input" name="cd"
                               value="${cd}" maxlength="3"
                               placeholder="科目コードを入力してください" />
                        <div class="text-warning">${errors.get('cd')}</div>
                    </div>
                </div>

                <%-- 科目名 --%>
                <div class="mb-3 row">
                    <label class="col-sm-2 col-form-label" for="subject-name-input">科目名</label>
                    <div class="col-sm-4">
                        <input class="form-control <c:if test="${!empty errors.get('name')}">is-invalid</c:if>"
                               type="text" id="subject-name-input" name="name"
                               value="${name}" maxlength="20"
                               placeholder="科目名を入力してください" />
                        <div class="text-warning">${errors.get('name')}</div>
                    </div>
                </div>

                <%-- 登録ボタン --%>
                <div class="row mt-4">
                    <div class="col-sm-6">
                        <input class="btn btn-primary px-5" type="submit" value="登録" />
                    </div>
                </div>

                <div class="mt-3">
                    <a href="SubjectList.action">戻る</a>
                </div>

            </form>
        </section>
    </c:param>
</c:import>
