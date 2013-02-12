<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>TagWin::Show</title>
</head>
<body>
<h1>stripHtml-Tag</h1>
<g:set var="fooBar" value="${'<h1>Foo<i>Bar</i>Bazz</h1>'}"/>
<p>
    input value: ${fooBar.encodeAsHTML()}
</p>
<p>
    stripHTML-Result: '<g:stripHtml value="${fooBar}"/>'
</p>

</body>
</html>