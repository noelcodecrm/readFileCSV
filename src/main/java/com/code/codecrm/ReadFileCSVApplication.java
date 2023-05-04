package com.code.codecrm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.code.codecrm.service.ReadFileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@ComponentScan("com.code.codecrm")
public class ReadFileCSVApplication implements CommandLineRunner {

	@Autowired
	private ReadFileService fileService;
	
	@Value("${proc.action.value}")
	private int actionValue;
	
	@Value("${proc.file.name.one}")
	private String fileNameOne;
	
	@Value("${proc.file.name.two}")
	private String fileNameTwo;
	
	

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ReadFileCSVApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			
			long startTime = System.nanoTime();
			log.info("Inicia proceso a las: " + startTime/1000000L);
			
			this.fileService.file(this.fileNameOne, this.fileNameTwo, this.actionValue);
			
			long endTime = System.nanoTime();
			long timeElapsed = endTime - startTime;
			long timeElapsedSecond = (timeElapsed / 1000000) / 1000;
			
			log.info("Execution time in nanoseconds: " + timeElapsed);
			log.info("Execution time in milliseconds: " + timeElapsed / 1000000);
			log.info("Execution time in minutes: " + timeElapsedSecond / 60);
			
		} catch (Exception e) {

			// se lanza la excepsion y se escribe en el log
			log.error("Error en la lectura o cración del archivo csv: {}", e.getMessage());
		}

	}
}
