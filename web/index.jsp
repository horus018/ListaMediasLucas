<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="listaAprovados" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="listaReprovados" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="msgErro" scope="session" class="java.lang.String"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/styles.css">
        <title>Médias</title>
    </head>
    <body>

        <h1>Dados do aluno</h1>
        <form action="calcular" method="POST">
            Nome: <input type="text" name="nome" size="80"/><br/>
            <!--verificar esse size nos input de nota se 3 é sulficiente, se sim, dxa se não fazer verificação no servlet pra converter numero pra entre 1 e 10-->
            Nota1: <input type="text" name="nota1"/>
            Nota2: <input type="text" name="nota2"/><br/>
            <input type="submit" value="Lançar"/>
        </form>
        <c:if test="${not empty msgErro}">
            <p style="color: red">${msgErro}</p>
        </c:if>

        <!--ver se é assim que compara com "ou" no empty do if, se um dos dois esta aparecendo coloca isso-->
        <c:if test="${not empty listaAprovados || not empty listaReprovados}">
            <h1>Resultados: </h1>
        </c:if>    

        <div style="display: flex">
            <!--tabela de aprovados-->
            <c:if test="${not empty listaAprovados}">
                <div style="margin-right: 20px">
                    <h1>Aprovados: </h1>
                    <table>
                        <tr>
                            <th>Nome</th>
                            <th>Nota1</th>
                            <th>Nota2</th>
                            <th>Média</th>
                        </tr>
                        <c:set var="pos" value="0" scope="request"/>
                        <c:forEach items="${listaAprovados}" var="aluno">
                            <tr>
                                <td>${aluno.nome}</td>
                                <td>${aluno.nota1}</td>
                                <td>${aluno.nota2}</td>
                                <td>${aluno.media}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>

            <!--tabela de reprovados-->
            <c:if test="${not empty listaReprovados}">
                <div>
                    <h1>Reprovados: </h1>
                    <table>
                        <tr>
                            <th>Nome</th>
                            <th>Nota1</th>
                            <th>Nota2</th>
                            <th>Média</th>
                        </tr>
                        <c:set var="pos" value="0" scope="request"/>
                        <c:forEach items="${listaReprovados}" var="aluno">
                            <tr>
                                <td>${aluno.nome}</td>
                                <td>${aluno.nota1}</td>
                                <td>${aluno.nota2}</td>
                                <td>${aluno.media}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
        </div>

        <c:if test="${not empty listaAprovados || not empty listaReprovados}">
            <form action="calcular" method="POST">
                <input type="hidden" name="acao" value="limpar"/>
                <input type="submit" value="Limpar dados"/>
            </form>
        </c:if>    

        <c:if test="${empty listaAprovados && empty listaReprovados}">
            <h2>Não há tarefas cadastradas</h2>
        </c:if>

        <c:remove var="msgErro" scope="session"/>
    </body>
</html>
