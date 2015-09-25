package hello;

import org.restexpress.RestExpress;
import org.restexpress.util.Environment;

import hello.config.Configuration;
import hello.controller.JsonController.HelloWorld;
import io.netty.handler.codec.http.HttpMethod;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		Configuration config = Environment.load(args, Configuration.class);
		RestExpress server = new RestExpress()
			.setName("RestExpress Benchmark")
		    .setExecutorThreadCount(config.getExecutorThreadPoolSize())
		    .alias("HelloWorld", HelloWorld.class);

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
