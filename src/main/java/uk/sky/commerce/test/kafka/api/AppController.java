package uk.sky.commerce.test.kafka.api;

import java.util.Random;
import java.util.stream.IntStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

	private final Random rand = new Random();

	@GetMapping
	public String getValue() throws InterruptedException {
		long before = System.currentTimeMillis();
		Thread.sleep(10 + rand.nextInt(10));
		return Integer.toHexString(IntStream.range(0, 100000).sum()) + " took " + (System.currentTimeMillis() - before) + "ms";
	}

}
