package com.code.codecrm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.code.codecrm.Pojos.ConcPago;
import com.code.codecrm.readFiles.IReadFilesToList;
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

	public void file(String fileNameOne, String fileNameTwo, int action) throws Exception {

		List<ConcPago> resultSequencesFileOne = this.action(fileNameOne, fileNameTwo, action);

		iwrFile.witeFileFilter(resultSequencesFileOne);

	}

	public List<ConcPago> action(String fileNameOne, String fileNameTwo, int action) throws Exception {

		List<ConcPago> resultSequencesFileOne = iReadFilesToList.readFile(fileNameOne);

		List<ConcPago> resultSequencesFileTwo = iReadFilesToList.readFile(fileNameTwo);
		List<ConcPago> result = new ArrayList<>();

		switch (action) {

		case 1:

			// remueve los registros que se encuentran en el segundo archivo
			resultSequencesFileOne.removeIf(
					a -> resultSequencesFileTwo.stream().anyMatch(b -> a.getTelefono().trim().equals(b.getTelefono().trim())));
			
			log.info("Se encontraron en total que no se encuentran en el segundo archivo: {}",
					result.size());
			
			return resultSequencesFileOne;
			
		case 2:
			// busca las coincidencias entre los dos archivos
			result = resultSequencesFileTwo.stream()
					.filter(e -> resultSequencesFileOne.stream()
							.map(ConcPago::getDefault5).anyMatch(tele -> tele.equals(e.getDefault5()))).collect(Collectors.toList());
			
			log.info("Se encontraron en total que se encuentran en el segundo archivo: {}",
					result.size());
			
			return result;
			
		default:

		}
		
		return result;
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) 
	{
	    Map<Object, Boolean> map = new ConcurrentHashMap<>();
	    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
}
