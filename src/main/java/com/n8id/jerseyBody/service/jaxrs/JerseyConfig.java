package com.n8id.jerseyBody.service.jaxrs;


import com.n8id.jerseyBody.service.OurRestService;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * invoke by springboot scan
 */
@Component
public class JerseyConfig extends ResourceConfig {

    private String apiPath = "/api/jerseyBody";

    @PostConstruct
    public void init() {
        // Register components where DI is needed
        this.configureSwagger();
    }

    public JerseyConfig() {
        this.register(OurRestService.class);
        this.register(WadlResource.class);
//        this.register(AuthFilter.class);
        this.register(JerseyRequestFilter.class);
//        this.register(JerseyResponseFilter.class);
//        this.register(ExceptionHandler.class);
//        this.register(ClientAbortExceptionWriterInterceptor.class);
    }

    private void configureSwagger() {
        // Available at localhost:port/swagger.json
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);

        BeanConfig config = new BeanConfig();
        config.setConfigId("config_id");
        config.setTitle("Our API");
        config.setSchemes(new String[]{"http", "https"});
        config.setBasePath(this.apiPath);
        config.setResourcePackage("com.n8id.jerseyBody.service");
        config.setPrettyPrint(true);
        config.setScan(true);
    }
}
