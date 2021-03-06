package hello.controller;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.restexpress.Request;
import org.restexpress.Response;

import hello.controller.persistence.WorldsMongodbRepository;

public class MongodbController
{
	// Database details.
	private static final int DB_ROWS = 10000;

	private WorldsMongodbRepository worldRepo;

	public MongodbController(WorldsMongodbRepository worldsRepository)
	{
		super();
		this.worldRepo = worldsRepository;
	}

	public Object read(Request request, Response response)
	{
		// Get the count of queries to run.
		int count = determineQueryCount(request);

		// Fetch some rows from the database.

		if (count == 1)
		{
			return worldRepo.find(generateIdentifier());
		}
		else
		{
			final Integer[] ids = generateIdentifiers(count);
			return worldRepo.findAll(Arrays.asList(ids));
		}
	}

	private int determineQueryCount(Request request)
	{
		String value = request.getHeader("queries");

		if (value == null) return 1;
		
		int count = Integer.parseInt(value);

		// Bounds check.
		if (count > 500)
		{
			count = 500;
		}
		else if (count < 1)
		{
			count = 1;
		}

		return count;
	}

	private Integer[] generateIdentifiers(int count)
	{
		final Integer[] ids = new Integer[count];
		final Random random = ThreadLocalRandom.current();

		for (int i = 0; i < count; i++)
		{
			ids[i] = random.nextInt(DB_ROWS) + 1;
		}

		return ids;
	}

	private int generateIdentifier()
	{
		return ThreadLocalRandom.current().nextInt(DB_ROWS) + 1;
	}
}
