package com.example.Servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lincg on 2017/5/10.
 */
public class Servlet extends HttpServlet {
      protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
          System.out.println("GETGETGET");
          doPost(req,resp);
      }

    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        System.out.println("POST");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h1>AaServlet</h1>");
    }

    @Bean
    public ServletRegistrationBean AaServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new Servlet());
        registration.addUrlMappings("/a");
        return registration;
    }


}
