package kr.co.ocean.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import kr.co.ocean.kafka.dto.KafkaJsonDTO;



@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Value(value = "${kafka.bootstrapServers}")
	private String bootstrapServers;
	
	@Value(value = "${kafka.consumer.groupId}")
	private String groupId;
	
	@Bean(name = "consumerFactory")
	public ConsumerFactory<String, KafkaJsonDTO> consumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, new JsonDeserializer<>(KafkaJsonDTO.class));
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(KafkaJsonDTO.class));
    }
	
	@Bean(name = "kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, KafkaJsonDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaJsonDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
//        factory.setRecordFilterStrategy(record -> record.key().contains("world"));
        return factory;
    }
	
//	public ConsumerFactory<String, Map> consumerFactoryForMap(){
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "jsonTest");
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        return new DefaultKafkaConsumerFactory<>(props,
//        		new StringDeserializer(),
//        		new JsonDeserializer<>(Map.class));
//    }
//	
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, Map> kafkaListenerContainerFactoryForMap() {
//		ConcurrentKafkaListenerContainerFactory<String, Map> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(consumerFactoryForMap());
//		return factory;
//	}
	
}
