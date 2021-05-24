package edu.pingpong.rest.json.resource;


import edu.pingpong.rest.json.domain.Fruit;
import edu.pingpong.rest.json.service.FruitService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/fruits")
public class FruitResource {

    @Inject
    FruitService service;

    public FruitResource() {
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fruitsData() {
        return Response.ok(service.getData(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addData(@Valid Fruit fruit) {
        service.addFruit(fruit);
        return Response.accepted(fruit).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteData(@Valid Fruit fruit) {
        service.removeFruit(fruit.getName());
        return Response.accepted(fruit).build();
    }

    @GET
    @Path("/{fruitname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData(@PathParam("fruitname") String fruitname) {
        Optional<Fruit> fruit = service.getFruit(fruitname);
        return fruit.isPresent() ? Response.ok(fruit).build() : Response.status(Response.Status.NOT_FOUND).entity("The fruit with name " + fruitname + " doesn't exist.").build();
    }
}