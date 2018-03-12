package net.a.g.s2i.wildflyswarm.demo.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.jboss.logging.Logger;

@ApplicationScoped
@Path("/")
public class HelloWorldEndpoint {

	Logger LOG = Logger.getLogger(HelloWorldEndpoint.class);

	@GET
	@Path("/hello")
	@Produces("text/plain")
	@Counted(monotonic = true)
	public Response getHello() {

		LOG.info("Call /hello");
		LOG.debug("Call /hello en mode debug");

		return Response.ok("Hello from WildFly ").build();
	}

	@GET
	@Path("/secret")
	@Produces("text/plain")
	@Counted(monotonic = true)
	public Response getSecret() {
		return Response.ok(System.getenv("MY_SECRET")).build();
	}

	@GET
	@Path("/configMap")
	@Produces("text/plain")
	@Counted(monotonic = true)
	public Response getConfigMap(@QueryParam("file") String fileUrl) throws IOException {
		File f = new File(fileUrl);
		if (f.exists()) {
			return Response.ok(new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())))).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
