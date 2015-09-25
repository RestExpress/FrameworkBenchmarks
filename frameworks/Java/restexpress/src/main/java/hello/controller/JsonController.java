package hello.controller;

import org.restexpress.Request;
import org.restexpress.Response;

public class JsonController
{
	private static final HelloWorld MESSAGE_OBJECT = new HelloWorld();

	public HelloWorld helloWorld(Request request, Response response)
	{
		return MESSAGE_OBJECT;
	}

	/**
	 * Inner class just to illustrate JSON serialization.
	 */
	public static class HelloWorld
	{
		@SuppressWarnings("unused")
        private String message = "Hello, World!";
	}
}
