<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>${grid.category} - 데이터</title>
    <link rel="stylesheet" type="text/css" href="/css/default.css" />
    <script>
        // 컨트롤러로 전달되는 카테고리 (redirect 대상)
        const CATEGORY = "${grid.category}";

        function searchTable() {
            const input = document.getElementById("searchInput");
            const filter = input.value.toLowerCase();
            const table = document.querySelector("table");
            const rows = table.getElementsByTagName("tr");

            for (let i = 1; i < rows.length; i++) {
                const cells = rows[i].getElementsByTagName("td");
                let match = false;

                for (let j = 0; j < cells.length; j++) {
                    if (cells[j].textContent.toLowerCase().includes(filter)) {
                        match = true;
                        break;
                    }
                }
                rows[i].style.display = match ? "" : "none";
            }
        }

        // 셀 엘리먼트 목록을 콤마구분 문자열로 조합
        function collectData(cells) {
            return Array.from(cells).map(function (c) {
                return c.textContent.trim();
            }).join(", ");
        }

        function insertItem() {
            const cells = document.querySelectorAll(".new-cell");
            const form = document.getElementById("itemForm");
            form.action = "/excel/data/insert";
            form.method = "post";
            form.category.value = CATEGORY;
            form.data.value = collectData(cells);
            form.submit();
        }

        function updateItem(seqNum) {
            const cells = document.querySelectorAll('.edit-cell[data-seq="' + seqNum + '"]');
            const form = document.getElementById("itemForm");
            form.action = "/excel/data/update/" + seqNum;
            form.method = "post";
            form.category.value = CATEGORY;
            form.data.value = collectData(cells);
            form.submit();
        }

        function deleteItem(seqNum) {
            if (confirm("정말 삭제하시겠습니까?")) {
                const form = document.getElementById("itemForm");
                form.action = "/excel/data/delete/" + seqNum;
                form.method = "post";
                form.category.value = CATEGORY;
                form.submit();
            }
        }

        // 현재 카테고리에 보이는 데이터를 엑셀 파일로 다운로드
        function downloadExcel() {
            location.href = "/excel/data/download?category=" + encodeURIComponent(CATEGORY);
        }
    </script>
</head>
<body>
    <h1>${grid.category}</h1>
    <ul>
        <li>
            🔎 검색 <input type="text" id="searchInput" onkeyup="searchTable()" /> |
            📥 엑셀 다운로드 <input type="button" value="DOWNLOAD" onclick="downloadExcel()" />
        </li>
    </ul>
    <table>
        <tr>
            <!-- 메타정보 컬럼 -->
            <c:forEach var="col" items="${grid.columns}">
                <th>${col}</th>
            </c:forEach>
            <th>액션</th>
        </tr>
        <!-- 신규 입력 행 -->
        <tr>
            <c:forEach var="col" items="${grid.columns}">
                <c:choose>
                    <%-- 수정일자/수정자는 입력받지 않고 저장 시 서버가 자동 생성 --%>
                    <c:when test="${col eq '수정일자' or col eq '수정자'}">
                        <td class="new-cell" style="color: #CCCCCC">(자동생성)</td>
                    </c:when>
                    <c:otherwise>
                        <td contenteditable='true' class="new-cell"></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <td>
                <input type="button" value="ADD" onclick="insertItem()" />
            </td>
        </tr>
        <!-- 데이터 행 -->
        <c:forEach var="row" items="${grid.rows}">
            <tr>
                <c:forEach var="col" items="${grid.columns}" varStatus="cs">
                    <c:choose>
                        <%-- 수정일자/수정자는 편집 불가(저장 시 서버가 자동 갱신) --%>
                        <c:when test="${col eq '수정일자' or col eq '수정자'}">
                            <td class="edit-cell" data-seq="${row.seqNum}" style="color: #CCCCCC">${row.cells[cs.index]}</td>
                        </c:when>
                        <c:otherwise>
                            <td contenteditable='true' class="edit-cell" data-seq="${row.seqNum}">${row.cells[cs.index]}</td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <td>
                    <input type="button" value="EDIT" onclick="updateItem(${row.seqNum})" />
                    <input type="button" value="DELETE" onclick="deleteItem(${row.seqNum})" />
                </td>
            </tr>
        </c:forEach>
    </table>
    <form method="post" id="itemForm">
        <input type="hidden" name="category" value="" />
        <input type="hidden" name="data" value="" />
    </form>
</body>
</html>
