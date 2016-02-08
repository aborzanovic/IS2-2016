/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import db.DB;
import entity.Company;
import entity.Component;
import java.util.List;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
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

/**
 *
 * @author Aleksandar
 */
@Stateless
@Path("restful/components")
public class Components{

    @Context private HttpServletRequest request;

    public Components() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createComponent(Component entity) {
        entity.setUsername(Login.getUsername(request));
        if(Login.isLoggedIn(request))
            DB.createComponent(entity);
        else
            System.out.println("Niste ulogovani!");
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void editComponent(@PathParam("id") int id, Component entity) {
        if(Login.isLoggedIn(request) && DB.hasComponent(Login.getUsername(request), id))
            DB.editComponent(entity);
        else
            System.out.println("Niste ulogovani!");
    }

    @DELETE
    @Path("{id}")
    public void removeComponent(@PathParam("id") int id) {
        if(Login.isLoggedIn(request) && DB.hasComponent(Login.getUsername(request), id))
            DB.removeComponent(id);
        else
            System.out.println("Niste ulogovani!");
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Component findComponent(@PathParam("id") int id) {
        if(Login.isLoggedIn(request))
            return DB.findComponent(id);
        else
            System.out.println("Niste ulogovani!");
        return null;
    }

    @GET
    @Path("company/{company_id}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Component> findAllComponentsByCompany(@PathParam("company_id") String company_id) {
        if(Login.isLoggedIn(request))
            return DB.findAllComponentsByCompany(company_id);
        else
            System.out.println("Niste ulogovani!");
        return null;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Component> findAllComponents() {
        if(Login.isLoggedIn(request))
            return DB.findAllComponents();
        else
            System.out.println("Niste ulogovani!");
        return null;
    }
    
    @PUT
    @Path("{id}/{sold}")
    public void registerSale(@PathParam("id") int id, @PathParam("sold") int sold){
        if(sold <= 0) return;
        if(Login.isLoggedIn(request) && DB.hasComponent(Login.getUsername(request), id)){
            Component com = DB.findComponent(id);
            if(sold > com.getAvailable()) return;
            com.setAvailable(com.getAvailable() - sold);
            DB.editComponent(com);
        }
        else
            System.out.println("Niste ulogovani!");
    }
}
