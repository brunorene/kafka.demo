package uk.sky.commerce.test.kafka.api;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Unchecked;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.runAsync;

@Slf4j
@Component
public class Listener {

	public ConcurrentLinkedDeque<String> dataQueue = new ConcurrentLinkedDeque<>();
	public final ExecutorService executor = Executors.newFixedThreadPool(250);

	@KafkaListener(topicPattern = "TOPIC5[0-9]*")
	public void listen(List<String> data) {
		Random rand = new Random();
		dataQueue.addAll(data);
		AtomicInteger count = new AtomicInteger();
		allOf(
			runAsync(Unchecked.runnable(() -> {
				String msg = data.get(count.getAndIncrement());
				if (msg != null) {
					Thread.sleep(500 + rand.nextInt(500));
				}
			}), executor)
		).join();
		log.info(Thread.currentThread() + " -> Current size " + dataQueue.size() + " -> " + data.size());
	}

}
