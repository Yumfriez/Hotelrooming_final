package by.tr.hotelbooking.controller.servlet;


import by.tr.hotelbooking.controller.command.Command;
import by.tr.hotelbooking.controller.command.CommandInvoker;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@MultipartConfig
public class FrontController extends HttpServlet {

    private static final String LOG4J_PARAM = "init_log4j";

    private final static long serialVersionUID=1L;

    public FrontController(){
        super();
    }

    @Override
    public void init() throws ServletException {
        String prefix = getServletContext().getRealPath("/");
        String filename = getInitParameter(LOG4J_PARAM);
        if(filename!=null){
            PropertyConfigurator.configure(prefix+ File.separator+filename);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command Command = CommandInvoker.getInstance().getCommand(request);
        Command.execute(request, response);
    }
}
