/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import db.DB;
import entity.Company;
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
@Path("restful/companies")
public class Companies{

    @Context private HttpServletRequest request;

    public Companies() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createCompany(Company entity) {
        DB.createCompany(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void editCompany(@PathParam("id") String id, Company entity) {
        if(Login.isLoggedIn(id, request))
            DB.editCompany(entity);
    }

    @DELETE
    @Path("{id}")
    public void removeCompany(@PathParam("id") String id) {
        if(Login.isLoggedIn(id, request))
            DB.removeCompany(id);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Company findCompany(@PathParam("id") String id) {
        if(Login.isLoggedIn(request))
            return DB.findCompany(id);
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Company> findAllCompanies() {
        if(Login.isLoggedIn(request))
            return DB.findAllCompanies();
        return null;
    }    
}
