<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<%
pageContext.setAttribute("result", "hello");
%>
<body>
<%=request.getAttribute("result") %> 입니다
${requestScope.result}
${names[0]}
${names[1]}
${notice.title}
${notice.id}
${result}
${param.n}
${header.accept}

</body>
</html>