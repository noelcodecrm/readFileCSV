package com.code.codecrm.readFilesImpl;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.code.codecrm.Pojos.ConcPago;
import com.code.codecrm.readFiles.IReadFilesToList;
import com.code.codecrm.utils.Numbers;
import com.opencsv.CSVReader;

import ch.qos.logback.classic.pattern.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ReadFilesToListImpl extends Util implements IReadFilesToList {

	private static String ROUTE = "C:/file/";

	@Value("${proc.field.position.region}")
	private int region;
	@Value("${proc.field.position.telefono}")
	private int telefono;
	@Value("${proc.field.position.cuenta}")
	private int cuenta;
	@Value("${proc.field.position.total}")
	private int total;
	@Value("${proc.field.position.fechahora}")
	private int fechahora;

	@Override
	public List<ConcPago> readFile(String nameFile) throws Exception {

		String[] nextLine = null;

		CSVReader reader = this.readFiles(nameFile);

		// lista para guaradar los registros en posicion de la columna ingresada
		List<ConcPago> sequenceConcPago = new ArrayList<>();

		while ((nextLine = reader.readNext()) != null) {

			if (!nextLine[Numbers.ZERO.getValue()].isEmpty()) {

				ConcPago concPago = new ConcPago();

				// busca la posición del campo del archivo
				concPago.setIdRegion(nextLine[this.region]);
				concPago.setTelefono(nextLine[this.telefono]);
				concPago.setCuenta(nextLine[this.cuenta]);
				concPago.setTotal(nextLine[this.total]);
				concPago.setFechaHora(nextLine[this.fechahora]);

				// asigna el objeto a la
				sequenceConcPago.add(concPago);
			}
		}
		reader.close();
		log.info("Se encontraron en total de registros: {}", sequenceConcPago.size());
		log.info("Se terminó de leer el archivo: {}", nameFile);
		return sequenceConcPago;
	}

	private CSVReader readFiles(String nameFile) throws IOException {

		CSVReader reader = null;

		// lectura del archivo en disco c:/file/
		reader = new CSVReader(new FileReader(ROUTE.concat(nameFile)));

		log.info("Leyendo archivo: {}", ROUTE.concat(nameFile));

		return reader;

	}

}
