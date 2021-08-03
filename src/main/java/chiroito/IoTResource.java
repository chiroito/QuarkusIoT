package chiroito;

import chiroito.device.Fan;
import chiroito.device.Led;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/iot")
public class IoTResource {

    @Inject
    Fan fan;

    @Inject
    Led led;

    @GET
    @Path("/stopFan")
    @Produces(MediaType.TEXT_PLAIN)
    public String stopFan(){
        fan.stop();
        return "Stop Fan";
    }

    @GET
    @Path("/warnLed")
    @Produces(MediaType.TEXT_PLAIN)
    public String warn(){
        led.warn();
        return "warn LED";
    }
}
