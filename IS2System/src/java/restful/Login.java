/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import db.DB;
import entity.Credentials;
import entity.Person;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aleksandar
 */
@Stateless
@Path("restful/login")
public class Login{

    @Context private HttpServletRequest request;

    public Login() {
    }

    @POST
    @Path("person")
    @Consumes(MediaType.APPLICATION_XML)
    public String loginPerson(Credentials entity) {
        if(DB.login(entity.getUsername(), entity.getPassword())){
            HttpSession session = request.getSession(true);
            session.setAttribute("login", true);
            session.setAttribute("username", entity.getUsername());
            return "success";
        }
        return "fail";
    }
    
    @POST
    @Path("company")
    @Consumes(MediaType.APPLICATION_XML)
    public String loginCompany(Credentials entity) {
        if(DB.login(entity.getUsername(), entity.getPassword())){
            HttpSession session = request.getSession(true);
            session.setAttribute("login", true);
            session.setAttribute("username", entity.getUsername());
            return "success";
        }
        return "fail";
    }
    
    @DELETE
    public void logout() {
        HttpSession session = request.getSession(true);
        session.invalidate();
    }
    
    public static boolean isLoggedIn(String username, HttpServletRequest request){
        return username.equals(request.getSession(true).getAttribute("username"));
    }
    
    public static boolean isLoggedIn(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        return session.getAttribute("login") != null && (boolean) session.getAttribute("login");
    }
    
    public static String getUsername(HttpServletRequest request){
        return (String) request.getSession(true).getAttribute("username");
    }
}
