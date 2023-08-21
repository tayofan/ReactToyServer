package kr.co.ocean.kafka.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import kr.co.ocean.kafka.dto.KafkaJsonDTO;

@Service
public class ProducerService {
	@Value(value = "${kafka.topic.name}")
	private String topicName;
	
	@Autowired
	private KafkaTemplate<String, KafkaJsonDTO> kafkaTemplate;
	
//	@Qualifier("kafkaTemplateForMap")
//	private KafkaTemplate<String, KafkaJsonDTO> kafkaTemplateForMap;
	
	public void sendMessage(String username, String type, KafkaJsonDTO message) {
		ProducerRecord<String, KafkaJsonDTO> producerRecord 
			= new ProducerRecord<String, KafkaJsonDTO>(topicName,type,message);
		producerRecord.headers().add(new RecordHeader("username", username.getBytes()));
		kafkaTemplate.send(producerRecord);
		
	}
	
//	public void sendMessageJson(KafkaJsonDTO message) {
//		kafkaTemplateForMap.send("springBootJson", message);
//	}
	
}
