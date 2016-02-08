/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import db.DB;
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

/**
 *
 * @author Aleksandar
 */
@Stateless
@Path("restful/persons")
public class Persons{

    @Context private HttpServletRequest request;

    public Persons() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void createPerson(Person entity) {
        DB.createPerson(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void editPerson(@PathParam("id") String id, Person entity) {
        if(Login.isLoggedIn(id, request))
            DB.editPerson(entity);
    }

    @DELETE
    @Path("{id}")
    public void removePerson(@PathParam("id") String id) {
        if(Login.isLoggedIn(id, request))
            DB.removePerson(id);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Person findPerson(@PathParam("id") String id) {
        if(Login.isLoggedIn(request))
            return DB.findPerson(id);
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Person> findAllPersons() {
        if(Login.isLoggedIn(request))
            return DB.findAllPersons();
        return null;
    }    
}
