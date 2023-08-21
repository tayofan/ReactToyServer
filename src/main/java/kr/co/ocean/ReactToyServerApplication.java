package kr.co.ocean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

import kr.co.ocean.config.PgDataSource;

@EnableKafka
@SpringBootApplication
@Import(value =  {PgDataSource.class})
public class ReactToyServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactToyServerApplication.class, args);
	}

}
