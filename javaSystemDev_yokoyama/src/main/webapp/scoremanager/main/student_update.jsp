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
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
            <div class="mx-4">
                <form action="StudentUpdateExecute.action" method="post">
                    <%-- --------------------------------------- --%>
                    <input type="hidden" name="no" value="${student.no}" />

                    <div class="mb-3">
                        <label class="form-label">学生番号</label>
                        <p class="form-control-plaintext fw-bold">${student.no}</p>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="name">氏名</label>
                        <input class="form-control" type="text" id="name" name="name"
                               maxlength="30" value="${student.name}" required />
                        <c:if test="${not empty errors.name}">
                            <div class="text-warning mt-1">${errors.name}</div>
                        </c:if>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="ent_year">入学年度</label>
                        <select class="form-select" id="ent_year" name="ent_year" required>
                            <option value="">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}"
                                    <c:if test="${year == student.entYear}">selected</c:if>
                                >${year}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label" for="class_num">クラス</label>
                        <select class="form-select" id="class_num" name="class_num" required>
                            <option value="">--------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}"
                                    <c:if test="${num == student.classNum}">selected</c:if>
                                >${num}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-4 form-check">
                        <input class="form-check-input" type="checkbox" id="is_attend"
                               name="is_attend" value="t"
                               <c:if test="${student.isAttend}">checked</c:if> />
                        <label class="form-check-label" for="is_attend">在学中</label>
                    </div>

                    <div class="mb-4">
                        <button class="btn btn-secondary me-2" type="submit">変更</button>
                        <a href="StudentList.action" class="btn btn-outline-secondary">戻る</a>
                    </div>
                    <%-- ------------------------------------------ --%>
                </form>
            </div>
        </section>
    </c:param>
</c:import>
