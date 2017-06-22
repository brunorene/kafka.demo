package uk.sky.commerce.test.kafka.api;

import java.util.stream.IntStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

	@GetMapping
	public String getValue() {
		long before = System.currentTimeMillis();
		 return Integer.toHexString(IntStream.range(0, 100000).sum())+ " took " + (System.currentTimeMillis() - before) + "ms";
	}

}
