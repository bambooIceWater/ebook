package com.sunshine.ebook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sunshine.ebook.mapper")
public class EbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbookApplication.class, args);
	}
}
