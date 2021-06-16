package com.authentication.agent;

import com.authentication.agent.entity.User;
import com.authentication.agent.enums.ClientType;
import com.authentication.agent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootApplication
@EnableEurekaClient
public class AuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return  new RestTemplate();
	}
	@Bean
	public CommandLineRunner createAdmin(UserRepository userRepository,PasswordEncoder passwordEncoder){
		return args -> {
			Optional<User> optionalUser = userRepository.findByUsername("admin@admin.com");
			if(!optionalUser.isPresent()){
					saveAdmin(passwordEncoder,userRepository);
			}
		};
	}

	private void saveAdmin(PasswordEncoder passwordEncoder,UserRepository userRepository) {
		User admin = new User();
		admin.setUsername("admin@admin.com");
		admin.setPassword(passwordEncoder.encode("administrator"));
		admin.setRole(ClientType.Administrator.toString());
		userRepository.save(admin);
	}
}
