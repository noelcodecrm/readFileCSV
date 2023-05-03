package com.code.codecrm.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
			break;
		case 2:
			
			
			for (ConcPago concPagoOne : resultSequencesFileOne) {
				
				for (ConcPago concPago2 : resultSequencesFileTwo) {
					ConcPago concPago = new ConcPago();
					
				  if(concPagoOne.getTelefono().trim().equals(concPago2.getTelefono().trim())) {
					concPago.setCuenta(concPago2.getCuenta());  
					concPago.setFechaHora(concPago2.getFechaHora());
					concPago.setIdRegion(concPago2.getIdRegion());
					concPago.setTotal(concPago2.getTotal());
					concPago.setTelefono(concPago2.getTelefono().trim());
					concPago.setDefault5(concPago2.getDefault5().trim());
					result.add(concPago);
				  }
				 
				}
			}
			
			
			
			
			
			
			
//			resultSequencesFileTwo.stream().findFirst().filter(a -> a.getTelefono().equals(contains));


			break;

		default:

		}
		
//		Set<ConcPago> set = new LinkedHashSet<>(result);
//		
//		List<ConcPago> result2 = new ArrayList<>(set);
		
		List<ConcPago> nuevo =  result.stream().filter(distinctByKey(p -> p.getTelefono())).collect(Collectors.toList());

		log.info("Se encontraron en total de registros que no tienen en el segundo archivo: {}",
				nuevo.size());

		return resultSequencesFileOne;
	}
	
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) 
	{
	    Map<Object, Boolean> map = new ConcurrentHashMap<>();
	    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
