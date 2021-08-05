package chiroito;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.concurrent.CompletionStage;

@Path("/")
@RegisterRestClient
public interface DeviceMonitorService {

    @POST
    @Path("/rest")
    CompletionStage<Void> postInfo(@FormParam("temparature") double temparature, @FormParam("fanSpeed") int fanSpeed, @FormParam("deviceName") String deviceName);
}
