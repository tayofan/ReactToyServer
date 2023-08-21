package kr.co.ocean.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import kr.co.ocean.kafka.dto.KafkaJsonDTO;

@Configuration
public class KafkaProducerConfig {
	
	@Value(value = "${kafka.bootstrapServers}")
	private String bootstrapServers;
	
	@Bean(name = "producerFactory")
	public ProducerFactory<String, KafkaJsonDTO> producerFactory() {

		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<>(configProps);
	}
	
//	@Bean(name = "producerFactoryForMap")
//	public ProducerFactory<String, KafkaJsonDTO> producerFactoryForMap() {
//		
//		Map<String, Object> configProps = new HashMap<>();
//		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
//		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializable.class);
//
//		return new DefaultKafkaProducerFactory(configProps);
//	}

	@Bean(name = "kafkaTemplate")
	public KafkaTemplate<String, KafkaJsonDTO> kafkaTemplate() {
		return new KafkaTemplate<String, KafkaJsonDTO>(producerFactory());
	}
	
//	@Bean(name = "kafkaTemplateForMap")
//	public KafkaTemplate<String, KafkaJsonDTO> kafkaTemplateForMap() {
//		return new KafkaTemplate<String, KafkaJsonDTO>(producerFactoryForMap());
//	}
}
