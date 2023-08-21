package kr.co.ocean.kafka.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ocean.kafka.dto.KafkaJsonDTO;
import kr.co.ocean.kafka.service.ConsumerService;
import kr.co.ocean.kafka.service.ProducerService;

@RestController
@RequestMapping(value="/kafka/test")
public class TestController {
	
	private final ProducerService producerService;
	private final ConsumerService consumerService;
	
	public TestController(ProducerService producerService,ConsumerService consumerService) {
		this.producerService = producerService;
		this.consumerService = consumerService;
	}
	
	@PostMapping(value="/message")
	public String sendMessage(@RequestBody KafkaJsonDTO param) {
//		producerService.sendMessage((String) param.get("message"));
		
		param.setDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now()));
		
		producerService.sendMessage("tjdtkd3869","serchKeyword",param);
		return "success";
	}
	
//	@PostMapping(value="/messageJson")
//	public String sendMessageJson(@RequestBody KafkaJsonDTO param) {
//		System.out.println(param);
//		producerService.sendMessageJson(param);
//		return "success";
//	}
}
