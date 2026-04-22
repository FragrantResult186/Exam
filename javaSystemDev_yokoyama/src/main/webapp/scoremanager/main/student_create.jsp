<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <c:import url="/common/base.jsp">
            <c:param name="title">
                得点管理システム
            </c:param>

            <c:param name="scripts"></c:param>

            <c:param name="content">
                <section class="mx-4">
                    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生新規登録</h2>
                    <div class="mx-4">
                        <form action="StudentCreateExecute.action" method="post">
                            
                            <div class="mb-3">
                                <label class="form-label" for="ent_year">入学年度</label>
                                <select class="form-select" id="ent_year" name="ent_year" required>
                                    <option value="">--------</option>
                                    <c:forEach var="year" items="${ent_year_set}">
                                        <option value="${year}">${year}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label" for="no">学生番号</label>
                                <input class="form-control" type="text" id="no" name="no" maxlength="10"
                                    placeholder="学生番号を入力してください" required />
                                <c:if test="${not empty errors.no}">
                                    <div class="text-warning mt-1">${errors.no}</div>
                                </c:if>
                            </div>
                            
                            <div class="mb-3">
                            
                                <label class="form-label" for="name">氏名</label>
                                <input class="form-control" type="text" id="name" name="name" maxlength="30"
                                    placeholder="氏名を入力してください" required />
                                <c:if test="${not empty errors.name}">
                                    <div class="text-warning mt-1">${errors.name}</div>
                                </c:if>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label" for="class_num">クラス</label>
                                <select class="form-select" id="class_num" name="class_num" required>
                                    <option value="">--------</option>
                                    <c:forEach var="num" items="${class_num_set}">
                                        <option value="${num}">${num}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            
                            <div class="mb-4">
                                <button class="btn btn-secondary me-2" type="submit">登録</button>
                                <a href="StudentList.action" class="btn btn-outline-secondary">戻る</a>
                            </div>
                            
                        </form>
                    </div>
                </section>
            </c:param>
        </c:import>