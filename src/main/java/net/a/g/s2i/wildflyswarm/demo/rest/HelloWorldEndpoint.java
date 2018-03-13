package net.a.g.s2i.wildflyswarm.demo.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.DefaultValue;
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

	// @GET
	// @Produces("text/html")
	// @Counted(monotonic = true)
	// public Response getHome() {
	// LOG.info("Call /");
	// LOG.debug("Call / en mode debug");
	// return Response
	// .ok("<html><a href=\"/hello\">hello</a><br/><a
	// href=\"/secret\">secret</a><br/><a
	// href=\"/configmap?file=/data/config.txt\">configMap</a><br/></html>")
	// .build();
	// }

	@GET
	@Produces("text/html")
	@Counted(monotonic = true)
	public Response getHome() throws IOException {
		LOG.info("Call /");
		LOG.debug("Call / en mode debug");
		InputStream is = this.getClass().getResourceAsStream("/index.html");
		return Response
				.ok(convert(is))
				.build();
	}

	public String convert(InputStream inputStream) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}

	@GET
	@Path("/hello")
	@Produces("text/plain")
	@Counted(monotonic = true)
	public Response getHello(@QueryParam("name") @DefaultValue("<userName>") String name) {

		LOG.info("Call /hello");
		LOG.debug("Call /hello en mode debug");

		return Response.ok("Hello " + name + " from WildFly ").build();
	}

	@GET
	@Path("/secret")
	@Produces("text/plain")
	@Counted(monotonic = true)
	public Response getSecret() {
		LOG.info("Call /secret");
		LOG.debug("Call /secret en mode debug");
		return Response.ok(System.getenv("MY_SECRET")).build();
	}

	@GET
	@Path("/configmap")
	@Produces("text/plain")
	@Counted(monotonic = true)
	public Response getConfigMap(@QueryParam("file") String fileUrl) throws IOException {
		LOG.info("Call /configmap");
		LOG.debug("Call /configmap en mode debug");
		File f = new File(fileUrl);
		if (f.exists()) {
			return Response.ok(new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())))).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

}
