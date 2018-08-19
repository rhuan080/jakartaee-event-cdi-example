package net.rhuanrocha.eventcdiexample.resource;

import net.rhuanrocha.eventcdiexample.event.EmailEvent;
import net.rhuanrocha.eventcdiexample.qualifier.EmailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("email")
public class EmailResource {

    @Inject
    @EmailSender
    private Event<EmailEvent> emailEvent;

    private Logger logger = LogManager.getLogger(this.getClass());

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendEmail(@Email @NotBlank @FormParam("email") String email,
                              @NotBlank @FormParam("message") String message ){


        logger.info("email:"+email + " message:"+message);

        emailEvent.fire(EmailEvent.build(email,message));

        return Response.ok().build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/async")
    public Response sendEmailAsync(@Email @NotBlank @FormParam("email") String email,
                              @NotBlank @FormParam("message") String message ){


        logger.info("email:"+email + " message:"+message);

        emailEvent.fireAsync(EmailEvent.build(email,message));

        return Response.ok().build();

    }
}
