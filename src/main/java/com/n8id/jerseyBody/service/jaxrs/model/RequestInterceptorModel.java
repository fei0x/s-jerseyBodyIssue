package com.n8id.jerseyBody.service.jaxrs.model;


import com.n8id.jerseyBody.service.jaxrs.JerseyRequestFilter;
import lombok.Data;
import org.apache.maven.wagon.authorization.AuthorizationException;
import org.glassfish.jersey.server.ContainerRequest;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jmoore on 20/09/16.
 */
@Data
public class RequestInterceptorModel {
    public static final String LOG_LEVEL__INFO = "INFO";
    public static final String LOG_LEVEL__ERROR = "ERROR";
    public static final String LOG_LEVEL__DEBUG = "DEBUG";
    public static final String LOG_LEVEL__TRACE = "TRACE";
    public static final String LOG_LEVEL__WARN = "WARN";
    public static final String LOG_LEVEL__NONE = "NONE";

    private ContainerRequestContext requestContext;
    private Method authorizationMethod;
    private Method method;
    private String logLevel;
    private List<String> licenseVersion;
    private Class<?> authorizationClass;
    private UUID txid;

    private String url;
    private String path;
    private String ipAddress;
    private String httpRequestMethod;
    private String httpRequestBody;
    private String httpRequestHeaders;
    private Map<String, String[]> parameterMap;

    private String initiator;
    private String subject;
    private String sourceSystem;
    private String sourceTxid;
    private String sourceReason;
    private UUID parentTxid;
    private String serviceType;


    public RequestInterceptorModel(ContainerRequestContext context, HttpServletRequest httpRequest, ResourceInfo resourceInfo) throws AuthorizationException, IOException {
        setTxid(UUID.fromString(context.getHeaderString(JerseyRequestFilter.HEADER_KEY__TXID)));
        setRequestContext(context);
        setHttpRequestMethod(getRequestContext().getMethod());
        setParameterMap(httpRequest.getParameterMap());
        setIpAddress(httpRequest.getRemoteAddr());
        setUrl(context.getUriInfo().getRequestUri().toString());
        String path = ((ContainerRequest) context).getPath(true);
        if (path.contains("/")) {
            path =  path.split("/")[1];
        }
        setPath(path);
        setSubject(context.getHeaderString(JerseyRequestFilter.HEADER_KEY_OPTIONAL__SUBJECT));
        setHttpRequestBody(JSONObject.wrap(parameterMap).toString());
        setHttpRequestHeaders(httpRequestHeadersAsString());

        setMethod(resourceInfo.getResourceMethod());



    }

    /**
     * get http request headers as a string
     */
    public String httpRequestHeadersAsString() {
        String httpReq = requestContext.getHeaders().keySet().stream()
                .filter(key -> !key.equalsIgnoreCase(HttpHeaders.AUTHORIZATION))
                .map(k -> k + ": " + requestContext.getHeaderString(k.toString()))
                .collect(Collectors.joining(System.lineSeparator()));
        return httpReq;
    }
}
