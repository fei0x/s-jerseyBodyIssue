
package com.n8id.jerseyBody.service;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("jerseyBody")
@Api(value = "api", produces = "application/json")
public class OurRestService {
    /**
     * just returns the param value
     *
     * @param param
     * @return
     * @throws
     */
    @POST
    @Path("ping")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String pingService(@FormParam("param") String param)  {
        return param;
    }

}