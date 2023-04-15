package com.code.codecrm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.code.codecrm.Pojos.ConcPago;
import com.code.codecrm.readFiles.IReadFilesToList;
import com.code.codecrm.utils.Numbers;
import com.code.codecrm.writeFile.IWriteFile;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReadFileService {

	@Autowired
	private IReadFilesToList iReadFilesToList;

	@Autowired
	private IWriteFile iwrFile;

	public ReadFileService() {
		super();
	}

	public void file(String[] nameFiles) throws Exception {

		List<ConcPago> resultSequencesFileOne = iReadFilesToList.readFile(nameFiles[Numbers.ZERO.getValue()]);

		List<ConcPago> resultSequencesFileTwo = iReadFilesToList.readFile(nameFiles[Numbers.ONE.getValue()]);

		// remueve los registros que se encuentran en el segundo archivo
		resultSequencesFileOne
				.removeIf(a -> resultSequencesFileTwo.stream().anyMatch(b -> a.getTelefono().equals(b.getTelefono())));

		log.info("Se encontraron en total de registros que no tienen en el segundo archivo: {}",
				resultSequencesFileOne.size());

		iwrFile.witeFileFilter(resultSequencesFileOne);

	}

}
