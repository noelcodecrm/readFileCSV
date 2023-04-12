package com.code.codecrm.readFilesImpl;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.code.codecrm.Pojos.ConcPago;
import com.code.codecrm.readFiles.IReadFilesToList;
import com.opencsv.CSVReader;

@Repository
public class ReadFilesToListImpl implements IReadFilesToList {

	private static String ROUTE = "C:/file/";

	@Value("${proc.field.position0}")
	private int position0;
	@Value("${proc.field.position1}")
	private int position1;
	@Value("${proc.field.position2}")
	private int position2;
	@Value("${proc.field.position3}")
	private int position3;
	@Value("${proc.field.position4}")
	private int position4;

	@Override
	public List<ConcPago> readFile(int positionField, String nameFile) throws Exception {

		String[] nextLine = null;

		CSVReader reader = this.readFiles(nameFile);

		// lista para guaradar los registros en posicion de la columna ingresada

		List<ConcPago> sequenceConcPago = new ArrayList<>();

		while ((nextLine = reader.readNext()) != null) {

			if (!nextLine[0].isEmpty()) {
				// busca la posición del campo del archivo
				// sequenceFieldFile.add(nextLine[positionField]);

				ConcPago concPago = new ConcPago();
				concPago.setIdRegion(nextLine[this.position0]);
				concPago.setTelefono(nextLine[this.position1]);
				concPago.setCuenta(nextLine[this.position2]);
				concPago.setTotal(nextLine[this.position3]);
				
//				DateTimeFormatter formmater = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
//				DateTime date = DateTime.parse(nextLine[this.position4],formmater);
				concPago.setFechaHora(nextLine[this.position4]);

				// asigna el objeto a la
				sequenceConcPago.add(concPago);
			}
		}
		reader.close();

		// sequenceFieldFile.removeIf(p -> p.contentEquals(""));

		return sequenceConcPago;
	}

	private CSVReader readFiles(String nameFile) throws IOException {

		CSVReader reader = null;

		// lectura del archivo en disco c:/file/
		reader = new CSVReader(new FileReader(ROUTE + nameFile));

		return reader;

	}

}
