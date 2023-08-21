package kr.co.ocean.kafka.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	
	private final AdminClient adminClient;
	
	public AdminService(AdminClient adminClient) {
		this.adminClient = adminClient;
	}
	
	public List<String> getTopicList() throws Exception {

		ListTopicsResult listTopicsResult = adminClient.listTopics();
		
		KafkaFuture<Map<String, TopicListing>> topics = listTopicsResult.namesToListings();
		
		Map<String, TopicListing> res = null;
		
		List list = new ArrayList<>();
		
		res = topics.get();

		for (String key : res.keySet()) {
			
			list.add(res.get(key).name());
			
		}
		
		return list;
	}
	
	public void getTopicInfos() throws Exception {
		DescribeTopicsResult result =  adminClient.describeTopics(getTopicList());
		KafkaFuture<Map<String, TopicDescription>> test = result.allTopicNames();
		Map<String, TopicDescription> test1 = test.get();
		
//		TopicDescription description = test1.get("springBoot");
//		List<TopicPartitionInfo> infos = description.partitions();
//		
//		TopicPartitionInfo info = infos.get(0);
//		info.
//		
//		System.out.println(test1);
		
		adminClient.close();
		
	}
	
}
