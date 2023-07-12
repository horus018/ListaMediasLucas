package controle;

import dados.Aluno;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Gerenciador", urlPatterns = {"/calcular"})
public class Gerenciador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession();

        ArrayList<Aluno> listaAprovados = (ArrayList<Aluno>) sessao.getAttribute("listaAprovados");
        ArrayList<Aluno> listaReprovados = (ArrayList<Aluno>) sessao.getAttribute("listaReprovados");

        if (listaAprovados == null) {
            listaAprovados = new ArrayList<Aluno>();
            sessao.setAttribute("listaAprovados", listaAprovados);
        }

        if (listaReprovados == null) {
            listaReprovados = new ArrayList<Aluno>();
            sessao.setAttribute("listaReprovados", listaReprovados);
        }

        //fazer botao de limpar dados somente quando tiver ao menos um dados em uma das listas
        String acao = request.getParameter("acao");

        //ver com prof se é pra reiniciar as duas listas ou somente um lançamento de nota
        if ("limpar".equals(acao)) {
            sessao.removeAttribute("listaAprovados");
            sessao.removeAttribute("listaReprovados");
            response.sendRedirect("index.jsp");
            return;
        }

        try {

            //dados do form
            String nome = request.getParameter("nome");
            String snota1 = request.getParameter("nota1");
            String snota2 = request.getParameter("nota2");

            Double nota1;
            Double nota2;

            if (nome == null || nome.trim().isEmpty()) {
                throw new Exception("Nome não pode ser vazio.");
            }

            if (snota1 == null || snota1.trim().isEmpty()) {
                throw new Exception("Nota1 não pode ser vazia.");
            } else if (Double.parseDouble(snota1) < 0) {
                throw new Exception("Nota1 não pode ser negativa.");
            } else if (Double.parseDouble(snota1) > 10) {
                throw new Exception("Nota1 não pode ser maior que 10.");
            } else {
                nota1 = Double.parseDouble(snota1);
            }

            if (snota2 == null || snota2.trim().isEmpty()) {
                throw new Exception("Nota2 não pode ser vazia.");
            } else if (Double.parseDouble(snota2) < 0) {
                throw new Exception("Nota2 não pode ser negativa.");
            } else if (Double.parseDouble(snota2) > 10) {
                throw new Exception("Nota2 não pode ser maior que 10.");
            } else {
                nota2 = Double.parseDouble(snota2);
            }

            Double media = (nota1 * 2 + nota2 * 3) / 5;

            if (media >= 7) {
                listaAprovados.add(new Aluno(nome, nota1, nota2, media));
            } else {
                listaReprovados.add(new Aluno(nome, nota1, nota2, media));
            }

        } catch(NumberFormatException pe){
            sessao.setAttribute("msgErro", "Digite apenas números");
        }
        
        catch (Exception ex) {
            sessao.setAttribute("msgErro", ex.getMessage());
        }
        response.sendRedirect("index.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
