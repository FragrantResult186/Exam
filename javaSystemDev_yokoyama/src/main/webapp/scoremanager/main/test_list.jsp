<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:import url="/common/base.jsp">
<c:param name="title">得点管理システム</c:param>

<c:param name="content">
<section class="mx-4">

    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

    <!-- 検索 -->
    <form method="get">
        <div class="row border mx-3 mb-3 py-2 align-items-center rounded">

            <!-- 入学年度 -->
            <div class="col-3">
                <label>入学年度</label>
                <select class="form-select" name="f1">
                    <option value="0">--------</option>
                    <c:forEach var="year" items="${ent_year_set}">
                        <option value="${year}" <c:if test="${year==f1}">selected</c:if>>
                            ${year}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- クラス -->
            <div class="col-3">
                <label>クラス</label>
                <select class="form-select" name="f2">
                    <option value="0">--------</option>
                    <c:forEach var="num" items="${class_num_set}">
                        <option value="${num}" <c:if test="${num==f2}">selected</c:if>>
                            ${num}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- 科目 -->
            <div class="col-2">
                <label>科目</label>
                <select class="form-select" name="f4">
                    <option value="">--------</option>
                    <c:forEach var="s" items="${subjects}">
                        <option value="${s.cd}" <c:if test="${s.cd==f4}">selected</c:if>>
                            ${s.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- 回数 -->
            <div class="col-2">
                <label>回数</label>
                <input type="number" class="form-control" name="f5" value="${f5}">
            </div>

            <!-- ボタン -->
            <div class="col-2 text-center">
                <button class="btn btn-secondary">検索</button>
            </div>

        </div>
    </form>

    <!-- 検索結果 -->
    <c:if test="${tests.size() > 0}">

        <!-- 科目＋回数 -->
        <div class="mb-2">
            科目：${subject.name}（${f5}回）
        </div>

        <table class="table table-hover">
            <tr>
                <th>入学年度</th>
                <th>クラス</th>
                <th>学生番号</th>
                <th>氏名</th>
                <th>点数</th>
            </tr>

            <c:forEach var="t" items="${tests}">
                <tr>
                    <td>${t.entYear}</td>
                    <td>${t.classNum}</td>
                    <td>${t.studentNo}</td>
                    <td>${t.studentName}</td>
                    <td>
                        <input type="number" class="form-control" value="${t.point}">
                    </td>
                </tr>
            </c:forEach>
        </table>

    </c:if>

    <c:if test="${tests.size() == 0}">
        <div>検索結果がありません</div>
    </c:if>

</section>
</c:param>
</c:import>