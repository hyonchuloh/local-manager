<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>엑셀 관리 테이블 목록</title>
    <link rel="stylesheet" type="text/css" href="/css/default.css" />
    <script>
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

        function insertCategory() {
            const category = document.getElementById("new_category").textContent.trim();
            const metaInfo = document.getElementById("new_metaInfo").textContent.trim();
            if (category === "" || metaInfo === "") {
                alert("카테고리명과 컬럼목록을 입력하세요.");
                return;
            }
            const form = document.getElementById("categoryForm");
            form.action = "/excel/category/insert";
            form.method = "post";
            form.category.value = category;
            form.metaInfo.value = metaInfo;
            form.submit();
        }

        // 카테고리 선택 시 데이터 그리드를 팝업으로 오픈
        function openData(category) {
            window.open(
                "/excel/data?category=" + encodeURIComponent(category),
                "excelData",
                "width=1536,height=800,scrollbars=yes,resizable=yes"
            );
        }

        // 카테고리 삭제 (해당 카테고리의 데이터도 함께 삭제됨)
        function deleteCategory(category) {
            if (!confirm(category + " 카테고리를 삭제하시겠습니까?\n해당 카테고리의 데이터도 모두 삭제됩니다.")) {
                return;
            }
            const form = document.getElementById("deleteForm");
            form.action = "/excel/category/delete";
            form.method = "post";
            form.category.value = category;
            form.submit();
        }
    </script>
</head>
<body>
    <h1>
        <img src="/images/bok_logo.png" class="h1-image"/>&nbsp;
        엑셀 관리 테이블 목록
    </h1>
    <ul>
        <li>
            🔎 검색 <input type="text" id="searchInput" onkeyup="searchTable()" />
        </li>
        <li>
            💡 카테고리를 선택(OPEN)하면 해당 데이터 그리드가 팝업으로 열립니다.
            신규 카테고리는 아래 첫 행에 카테고리명과 컬럼목록(콤마구분)을 입력 후 ADD 하세요.
            DELETE를 누르면 해당 카테고리와 그 안의 데이터가 모두 삭제됩니다.
        </li>
    </ul>
    <!-- 카테고리, 컬럼목록(메타정보), 행수, 생성일시, 생성자, 액션 -->
    <table>
        <tr>
            <th>카테고리</th>
            <th>컬럼목록</th>
            <th>행수</th>
            <th>생성일시</th>
            <th>생성자</th>
            <th>액션</th>
        </tr>
        <tr>
            <td contenteditable='true' id="new_category"></td>
            <td contenteditable='true' id="new_metaInfo" style="text-align: left;"></td>
            <td>-</td>
            <td>(자동생성)</td>
            <td>(자동생성)</td>
            <td>
                <input type="button" value="ADD" onclick="insertCategory()" />
            </td>
        </tr>
        <c:forEach var="item" items="${lists}">
            <tr>
                <td>${item.category}</td>
                <td style="text-align: left;">${item.metaInfo}</td>
                <td>${item.rowCount}</td>
                <td style="color: #CCCCCC">${item.createDate}</td>
                <td style="color: #CCCCCC">${item.createUser}</td>
                <td>
                    <input type="button" value="OPEN" onclick="openData('${item.category}')" />
                    <input type="button" value="DELETE" onclick="deleteCategory('${item.category}')" />
                </td>
            </tr>
        </c:forEach>
    </table>
    <form method="post" id="categoryForm">
        <input type="hidden" name="category" value="" />
        <input type="hidden" name="metaInfo" value="" />
    </form>
    <form method="post" id="deleteForm">
        <input type="hidden" name="category" value="" />
    </form>
</body>
</html>
