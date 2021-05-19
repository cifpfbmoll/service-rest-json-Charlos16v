package edu.pingpong.rest.json;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/fruits")
public class FruitResource {

   @Inject
   FruitService service;

    public FruitResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fruitsData() {
        return Response.ok(service.getData()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addData(Fruit fruit) {
        service.addFruit(fruit);
        return Response.accepted(fruit).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteData(Fruit fruit) {
        service.removeFruit(fruit.getName());
        return Response.accepted(fruit).build();
    }
}