package com.flexiwms.business.apis.test;

import com.flexiwms.platform.infrastructure.operation.OperationContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.container.AsyncResponse;


@Path("/api/v1/")
@Api(value = "Test API")
public class TestController {

    private static final Logger log = LogManager.getLogger(TestController.class);

    @GET
    @Path("/hello")
    @ApiOperation(value = "Returns a greeting message")
    @Consumes(MediaType.APPLICATION_JSON)
    public void hello(@Context HttpHeaders header,
                      @Suspended final AsyncResponse asyncResponse,
                      @Context OperationContext operationContext) {
        log.info("Received request for /hello endpoint");
        String greeting = "Hello, World!";
        asyncResponse.resume(greeting);

    }
}
