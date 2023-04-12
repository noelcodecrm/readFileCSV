package com.code.codecrm;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.code.codecrm.service.ReadFileService;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

@SpringBootApplication
@ComponentScan("com.code.codecrm")
public class DemoApplication implements CommandLineRunner{
	
	@Autowired
    ApplicationContext applicationContext;
	
	
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
	
	}
	


	public void readExcelFile() throws Exception {
			

		List<String> groupTel = new ArrayList<>();
		
		List<String> groupFilterTel = null;
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(
					"C:/SICATEL/file/_SELECT_ID_REGION_TELEFONO_CUENTA_REPLACE_TOTAL_AS_TOTAL_FECHA_H_202304051604_2.csv"));
			String[] nextLine = null;

			while ((nextLine = reader.readNext()) != null) {
				groupTel.add(nextLine[1]);

				// System.out.println(Arrays.toString(nextLine));
			}
			
			List<String> groupTelPagosHW = new ArrayList<>();
			groupTelPagosHW.add("5534028860");
			
			Predicate<String> contains = groupTelPagosHW::contains;
			
			groupFilterTel = groupTel.stream().filter(contains.negate()).collect(Collectors.toList());
			

			
			
		} catch (Exception e) {
		} finally {
			if (null != reader) {
				reader.close();
			}
		}

		this.writeToCsv(groupFilterTel);

	}

	public void writeToCsv(List<String> tels) {
		String csv = "C:/SICATEL/file/salida.csv";
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(csv));
			tels.stream().forEach(val -> {
				Stream.of(val.split(","));
				String[] record = val.split(",");
				writer.writeNext(record);
			});

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(args[0]);
		
		ReadFileService fileService = applicationContext.getBean(ReadFileService.class);
		
		fileService.file();
		
	}
}
