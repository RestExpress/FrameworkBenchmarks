package hello;

import org.restexpress.RestExpress;
import org.restexpress.util.Environment;

import hello.config.Configuration;
import hello.controller.JsonController.HelloWorld;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;

public class Main
{
	static
	{
		// Netty optimization.
		ResourceLeakDetector.setLevel(Level.DISABLED);
	}

	public static void main(String[] args) throws Exception
	{
		Configuration config = Environment.load(args, Configuration.class);
		RestExpress server = new RestExpress()
			.setName("RestExpress Benchmark")
		    .setExecutorThreadCount(config.getExecutorThreadPoolSize())
		    .alias("HelloWorld", HelloWorld.class)
		    // response compression takes time...
		    .noCompression();

		server.uri("/restexpress/json", config.getJsonController())
			.action("helloWorld", HttpMethod.GET);

		server.uri("/restexpress/mysql", config.getMysqlController())
			.method(HttpMethod.GET);

		server.uri("/restexpress/mongodb", config.getMongodbController())
		    .method(HttpMethod.GET);

		server.bind(config.getPort());
		server.awaitShutdown();
	}
}
