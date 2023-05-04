package com.code.codecrm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public void file(String[] nameFiles, int action) throws Exception {

		List<ConcPago> resultSequencesFileOne = this.action(nameFiles, action);

		iwrFile.witeFileFilter(resultSequencesFileOne);

	}

	public List<ConcPago> action(String[] nameFiles, int action) throws Exception {

		List<ConcPago> resultSequencesFileOne = iReadFilesToList.readFile(nameFiles[Numbers.ZERO.getValue()]);

		List<ConcPago> resultSequencesFileTwo = iReadFilesToList.readFile(nameFiles[Numbers.ONE.getValue()]);
		List<ConcPago> result = new ArrayList<>();

		switch (action) {

		case 1:

			// remueve los registros que se encuentran en el segundo archivo
			resultSequencesFileOne.removeIf(
					a -> resultSequencesFileTwo.stream().anyMatch(b -> a.getTelefono().trim().equals(b.getTelefono().trim())));
			
			result = resultSequencesFileOne;
			return result;
		case 2:
			
			result = resultSequencesFileTwo.stream()
					.filter(e -> resultSequencesFileOne.stream()
							.map(ConcPago::getDefault5)
							.anyMatch(tele -> tele.equals(e.getDefault5())))
					.collect(Collectors.toList());
			
			//List<ConcPago> nuevo =  result.stream().filter(distinctByKey(p -> p.getDefault5().trim())).collect(Collectors.toList());

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
