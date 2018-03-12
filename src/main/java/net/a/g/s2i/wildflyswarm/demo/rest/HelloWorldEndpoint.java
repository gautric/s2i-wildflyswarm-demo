package net.a.g.s2i.wildflyswarm.demo.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.annotation.Counted;

@ApplicationScoped
@Path("/")
public class HelloWorldEndpoint {

	@GET
	@Path("/hello")
	@Produces("text/plain")
	@Counted(monotonic = true)
	public Response getHello() {
		return Response.ok("Hello from WildFly ").build();
	}

}
