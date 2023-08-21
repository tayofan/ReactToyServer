package kr.co.ocean.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
	
	private Logger logger = LoggerFactory.getLogger(ConsumerService.class);
	
	@KafkaListener(topics = "springBoot", groupId = "springBoot"/*, autoStartup = "false"*/)
	public void receive(ConsumerRecord<?, ?> record) {
		
		logger.info("record: " + record.toString());
		
		Header header = record.headers().lastHeader("username");
		logger.info("header : " + header.toString());
		logger.info("header key : " + header.key());
		logger.info("header value: " + new String(header.value()));				
	}

}
