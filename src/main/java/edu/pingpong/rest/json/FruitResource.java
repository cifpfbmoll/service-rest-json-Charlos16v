package edu.pingpong.rest.json;

import org.jboss.logging.annotations.Pos;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/fruits")
public class FruitResource {

   @Inject
   FruitService service;

    public FruitResource() {
    }

    @GET
    public Response fruitsData() {
        return Response.ok(service.getData()).build();
    }
    /*
    @POST
    public Set<Fruit> add(Fruit fruit) {
        fruitSet.add(fruit);
        return fruitSet;
    }*/
}