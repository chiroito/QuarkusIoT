package chiroito;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletionStage;

@Path("/")
@RegisterRestClient
public interface DeviceMonitorService {

    @GET
    @Path("/rest")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    void postInfo(@QueryParam("temparature") double temparature, @QueryParam("fanSpeed") int fanSpeed, @QueryParam("deviceName") String deviceName);
}
