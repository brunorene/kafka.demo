package uk.sky.commerce.test.kafka;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ApplicationStartup {

	private final KafkaTemplate<Integer, String> kafkaTemplate;

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		Executors.newScheduledThreadPool(2)
			.schedule(() -> {
				for (int i = 0; i < 1_000_000; i++) {
					kafkaTemplate.send("TOPIC5", "HELLO " + UUID.randomUUID() + " " + LocalDateTime.now());
				}
				log.info("*** messages sent ***");
			}, 30, TimeUnit.SECONDS);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory
	, KafkaProperties kafkaProperties) {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		// enable batch listeners
		factory.setBatchListener(true);
		BeanUtils.copyProperties(kafkaProperties.getListener(), factory);
		return factory;
	}

}
