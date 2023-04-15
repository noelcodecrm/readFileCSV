package com.code.codecrm.writeFileImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.code.codecrm.Pojos.ConcPago;
import com.code.codecrm.utils.Utils;
import com.code.codecrm.writeFile.IWriteFile;
import com.opencsv.CSVWriter;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WriteFileImpl implements IWriteFile {

	private static String ROUTE = "C:/file/";

	private static String NAME_OUT_FILE_WRITE = "outFile.csv";

	private static String NAME__OUT_FILE_SQL_WRITE_TXT = "sql.txt";
	
	@Autowired
	private Utils utils;

	@Override
	public String witeFileFilter(List<ConcPago> resultSequeseFilter) throws Exception {

		// escribe el resultado del filtro del archivo
		CSVWriter writer = new CSVWriter(new FileWriter(ROUTE.concat(NAME_OUT_FILE_WRITE)));

		resultSequeseFilter.stream().forEach(val -> {

			String sg = val.getIdRegion().concat(",").concat(val.getTelefono()).concat(",").concat(val.getCuenta())
					.concat(",").concat(val.getTotal()).concat(",").concat(val.getFechaHora().toString());

			String[] record = sg.split(",");

			// escribe en el archivo
			writer.writeNext(record);

		});

		writer.close();
		this.writeFileSql(resultSequeseFilter);
		log.info("Termina la creación del archivo cvs: {}", ROUTE.concat(NAME_OUT_FILE_WRITE));
		return null;
	}

	@Override
	public String writeFileSql(List<ConcPago> resultSequeseFilter) throws Exception {
		// TODO Auto-generated method stub

		File file = new File(ROUTE.concat(NAME__OUT_FILE_SQL_WRITE_TXT));
		// Si el archivo no existe es creado
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);

		resultSequeseFilter.stream().forEach(val -> {
			try {

				LocalDate date = LocalDate.parse(val.getFechaHora(),this.utils.getDateValue(this.utils.parseDefaulting));

				String sql = "update conc_pago2 set pago_bes= ".concat(val.getIdRegion())
						.concat(" where date(fecha_hora)='").concat(date.toString()).concat("' and telefono = '")
						.concat(val.getTelefono()).concat("' and id_concepto=").concat("101").concat(" and id_region= ")
						.concat(val.getIdRegion()).concat(",");

				bw.write(sql);
				bw.newLine();
			} catch (IOException e) {

				log.error("Ocurrió un error al crear el archivo:  {}", e.getMessage());

				e.printStackTrace();
			}
		});

		bw.close();
		log.info("Termina la creación del archivo txt->sql:  {}", ROUTE.concat(NAME__OUT_FILE_SQL_WRITE_TXT));
		return null;
	}

}
