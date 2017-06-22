package uk.sky.commerce.test.kafka.api;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Unchecked;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Listener {

	public Collection<String> dataQueue = new ConcurrentLinkedDeque<>();

	@KafkaListener(topicPattern = "TOPIC[0-9]+")
	public void listen(List<String> data) {
		Random rand = new Random();
		dataQueue.addAll(data);
		data.parallelStream().forEach(Unchecked.consumer(msg -> Thread.sleep(rand.nextInt(100))));
		log.info(Thread.currentThread() + " -> Current size " + dataQueue.size() + " -> " + data.size());
	}

}
