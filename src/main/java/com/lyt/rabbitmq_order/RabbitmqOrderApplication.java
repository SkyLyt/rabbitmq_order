package com.lyt.rabbitmq_order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.lyt.rabbitmq_order.mapper")
@SpringBootApplication
public class RabbitmqOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqOrderApplication.class, args);
	}

}
