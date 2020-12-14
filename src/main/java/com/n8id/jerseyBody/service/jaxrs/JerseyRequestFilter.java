package com.n8id.jerseyBody.service.jaxrs;


import com.n8id.jerseyBody.service.jaxrs.model.RequestInterceptorModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.wagon.authorization.AuthorizationException;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class JerseyRequestFilter implements ContainerRequestFilter {

    public static final String HEADER_KEY__TXID = "txid";
    public static final String HEADER_KEY__INITIATOR = "initiator";
    public static final String HEADER_KEY__SOURCE_SYSTEM_UUID = "source-system-uuid";
    public static final String HEADER_KEY__SOURCE_SYSTEM_TXID = "source-system-txid";
    public static final String HEADER_KEY__SOURCE_SYSTEM_REASON = "source-system-reason";
    public static final String HEADER_KEY__SERVICE_TYPE = "service-type";
    public static final String HEADER_KEY__PARENT_TXID = "parent-txid";
    public static final String HEADER_KEY_OPTIONAL__SUBJECT = "subject";
    public static final String HEADER_KEY_IP_ADDRESS= "x-real-ip";

    private static Logger logger = LogManager.getLogger(JerseyRequestFilter.class);

    @Context
    private ResourceInfo resourceInfo;
    @Context
    private HttpServletRequest httpRequest;

    public void filter(ContainerRequestContext context) throws IOException {

        context.getHeaders().putSingle(HEADER_KEY__TXID, UUID.randomUUID().toString());

        try {
            doRequestSteps(new RequestInterceptorModel(context, httpRequest, resourceInfo));
        } catch (AuthorizationException e) {
            throw new IOException(e);
        }
    }

    private void doRequestSteps(RequestInterceptorModel req) {
        Date now = new Date();

        logger.info(req.getHttpRequestBody());

    }

}
