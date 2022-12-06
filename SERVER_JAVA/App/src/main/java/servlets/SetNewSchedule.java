package servlets;

import db.Schedule;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.Log;
import logic.Logic;

/**
 *
 * @author mfran
 */
public class SetNewSchedule extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.log.info("-- Register new schedule " + request.getParameter("idSchedule")+", Comedero" + 
                request.getParameter("idFeeder") + " --");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            
            Schedule newSchedule = new Schedule();
            ArrayList<Integer> idSchedules = new ArrayList<Integer>();
            ArrayList<Schedule> schedules = Logic.getSchedulesUserFromDB(request.getParameter("idFeeder"));
            
            for (int i = 0; i < schedules.size(); i++) {
                idSchedules.add(schedules.get(i).getIdSchedule());
            }
            
            if (idSchedules.contains(Integer.parseInt(request.getParameter("idSchedule")))) {
                
            }


        } catch (NumberFormatException nfe) {
            out.println("-1");
            Log.log.error("Number Format Exception: {}", nfe);
                
        } catch (IndexOutOfBoundsException iobe) {
            out.println("-1");
            Log.log.error("Index out of bounds Exception: {}", iobe);
                
        } catch (Exception e) {
            out.println("-1");
            Log.log.error("Exception: {}", e);
                
        } finally {
            out.close();
        }
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
