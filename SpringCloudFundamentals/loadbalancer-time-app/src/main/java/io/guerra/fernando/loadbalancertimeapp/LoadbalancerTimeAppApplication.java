package io.guerra.fernando.loadbalancertimeapp;

import io.guerra.config.fernando.LoadBalancerTimeConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

@RestController
//@EnableDiscoveryClient
@LoadBalancerClient(name = "time-service", configuration = LoadBalancerTimeConfig.class)
@SpringBootApplication
public class LoadbalancerTimeAppApplication {

	@Inject
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(LoadbalancerTimeAppApplication.class, args);
	}

	@GetMapping
	public String getTime() {
		return restTemplate.getForEntity("http://time-service", String.class).getBody();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
