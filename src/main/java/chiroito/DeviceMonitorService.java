package chiroito;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import java.util.concurrent.CompletionStage;

@Path("/")
@RegisterRestClient
public interface DeviceMonitorService {

    @GET
    @Path("/rest")
    void postInfo(@QueryParam("temparature") double temparature, @QueryParam("fanSpeed") int fanSpeed, @QueryParam("deviceName") String deviceName);
}
