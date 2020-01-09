
package org.salem.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@EnableConfigurationProperties(org.salem.domain.file.StorageProperties.class)
@SpringBootApplication
public class MariaJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MariaJpaApplication.class, args);
	}

}