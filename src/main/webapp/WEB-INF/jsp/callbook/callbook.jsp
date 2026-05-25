<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>연락처</title>
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

        function downloadExcel() {
            // 엑셀 다운로드 기능 구현
            alert("엑셀 다운로드 기능은 아직 구현되지 않았습니다.");
        }

        function insertItem() {
            const form = document.getElementById("itemForm");
            form.action = "/callbook/insert";
            form.method = "post";
            form.company.value = document.getElementById("new_company").textContent.trim();
            form.department.value = document.getElementById("new_department").textContent.trim();
            form.name.value = document.getElementById("new_name").textContent.trim();
            form.phoneNumber.value = document.getElementById("new_phoneNumber").textContent.trim();
            form.email.value = document.getElementById("new_email").textContent.trim();
            form.memo.value = document.getElementById("new_memo").textContent.trim();
            form.submit();
        }

        function updateItem(id) {
            const form = document.getElementById("itemForm");
            form.action = "/callbook/update/" + id;
            form.method = "post";
            form.company.value = document.getElementById("edit_" + id + "_extName").textContent.trim();
            form.department.value = document.getElementById("edit_" + id + "_department").textContent.trim();
            form.name.value = document.getElementById("edit_" + id + "_name").textContent.trim();
            form.phoneNumber.value = document.getElementById("edit_" + id + "_phoneNumber").textContent.trim();
            form.email.value = document.getElementById("edit_" + id + "_email").textContent.trim();
            form.memo.value = document.getElementById("edit_" + id + "_memo").textContent.trim();
            form.submit();
        }

        function deleteItem(id) {
            if (confirm("정말 삭제하시겠습니까?")) {
                const form = document.getElementById("itemForm");
                form.action = "/callbook/delete/" + id;
                form.method = "post";
                form.submit();
            }
        }
    </script>
</head>
<body>
    <h1>연락처</h1>
    <ul>
        <li>
            🔎 검색 <input type="text" id="searchInput" onkeyup="searchTable()" /> | 
            📥 엑셀 다운로드 <input type="button" value="DOWNLOAD" onclick="downloadExcel()" />
        </li>
    </ul>
    <!-- ID, 이름, 전화번호, 이메일, 회사, 메모, 생성일시, 수정일시 -->
    <table>
        <tr>
            <th>ID</th>
            <th>회사</th>
            <th>부서</th> 
            <th>이름</th>
            <th>전화번호</th>
            <th>이메일</th>
            <th>메모</th>
            <!-- th>생성일시</th -->
            <th>최근수정일시</th>
            <th>액션</th>
        </tr>
        <tr>
            <td>신규</td>
            <td contenteditable='true' id="new_company"></td>
            <td contenteditable='true' id="new_department"></td>
            <td contenteditable='true' id="new_name"></td>
            <td contenteditable='true' id="new_phoneNumber"></td>
            <td contenteditable='true' id="new_email"></td>
            <td contenteditable='true' id="new_memo"></td>
            <!-- td>(자동생성)</td -->
            <td>(자동생성)</td>
            <td>
                <input type="button" value="ADD" onclick="insertItem()" />
            </td>
        </tr>
        <c:forEach var="item" items="${lists}">
            <tr>
                <td>${item.id}</td>
                <td contenteditable='true' id="edit_${item.id}_extName" >${item.company}</td>
                <td contenteditable='true' id="edit_${item.id}_department" >${item.department}</td>
                <td contenteditable='true' id="edit_${item.id}_name" >${item.name}</td>
                <td contenteditable='true' id="edit_${item.id}_phoneNumber" >${item.phoneNumber}</td>
                <td contenteditable='true' id="edit_${item.id}_email" >${item.email}</td>
                <td style="text-align: left;" contenteditable='true' id="edit_${item.id}_memo" >${item.memo}</td>
                <!-- td>${item.createdAt}</td -->
                <td style="color: #CCCCCC">${item.updatedAt}</td>
                <td>
                    <input type="button" value="EDIT" onclick="updateItem(${item.id})" />
                    <input type="button" value="DELETE" onclick="deleteItem(${item.id})" />
                </td>
            </tr>
        </c:forEach>
    </table>
    <form method="post" id="itemForm">
        <input type="hidden" name="company" value="" />
        <input type="hidden" name="department" value="" />
        <input type="hidden" name="name" value="" />
        <input type="hidden" name="phoneNumber" value="" />
        <input type="hidden" name="email" value="" />
        <input type="hidden" name="memo" value="" />
    </form>
</body>
</html>
