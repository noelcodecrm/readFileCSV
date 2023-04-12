package com.code.codecrm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.code.codecrm.Pojos.ConcPago;
import com.code.codecrm.readFiles.IReadFilesToList;
import com.code.codecrm.writeFile.IWriteFile;

@Component
public class ReadFileService{
	
	@Autowired
	private IReadFilesToList iReadFilesToList;
	
	@Autowired
	private IWriteFile iwrFile;
	
	public ReadFileService() {
		super();
	}
	
	public void file() throws Exception {
		
		List<ConcPago> resultSequencesFileOne = iReadFilesToList.readFile(1, "file1.csv");
		
		List<ConcPago> resultSequencesFileTwo = iReadFilesToList.readFile(1, "file2.csv");

		//remueve los registros que se encuentran en el segundo archivo
		resultSequencesFileOne.removeIf(a -> resultSequencesFileTwo.stream().anyMatch(b -> a.getTelefono().equals(b.getTelefono())));
		
		System.out.println(resultSequencesFileOne);
		System.out.println(resultSequencesFileTwo);
		
		iwrFile.witeFileFilter(resultSequencesFileOne);
		
	}

	
	
	
}
