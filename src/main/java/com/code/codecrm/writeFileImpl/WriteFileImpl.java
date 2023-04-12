package com.code.codecrm.writeFileImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.code.codecrm.Pojos.ConcPago;
import com.code.codecrm.writeFile.IWriteFile;
import com.opencsv.CSVWriter;

@Component
public class WriteFileImpl implements IWriteFile {

	private static String ROUTE = "C:/file/";

	private static String NAME_FILE_WRITE = "outFile.csv";

	@Value("${proc.date.parseDefaulting}")
	private Long parseDefaulting;

	@Override
	public String witeFileFilter(List<ConcPago> resultSequeseFilter) throws Exception {

		// escribe el resultado del filtro del archivo
		CSVWriter writer = new CSVWriter(new FileWriter(ROUTE + NAME_FILE_WRITE));

		resultSequeseFilter.stream().forEach(val -> {

			Stream.of(val.getIdRegion());

			String sg = val.getIdRegion().concat(",").concat(val.getTelefono()).concat(",").concat(val.getCuenta())
					.concat(",").concat(val.getTotal()).concat(",").concat(val.getFechaHora().toString());

			String[] record = sg.split(",");

			// escribe en el archivo
			writer.writeNext(record);

		});

		writer.close();
		this.writeFileSql(resultSequeseFilter);

		return null;
	}

	@Override
	public String writeFileSql(List<ConcPago> resultSequeseFilter) throws Exception {
		// TODO Auto-generated method stub

		String ruta = "C:/file/filename.txt";
		File file = new File(ruta);
		// Si el archivo no existe es creado
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);

		DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder().parseCaseInsensitive().parseLenient()
				.parseDefaulting(ChronoField.YEAR_OF_ERA, this.parseDefaulting).appendPattern("[yyyy-MM-dd]")
				.appendPattern("[M/dd/yyyy]").appendPattern("[M/d/yyyy]").appendPattern("[MM/dd/yyyy]")
				.appendPattern("[MMM dd yyyy]").appendPattern("[dd/MM/yyyy]");

		DateTimeFormatter formatter2 = builder.toFormatter(Locale.ENGLISH);

		resultSequeseFilter.stream().forEach(val -> {
			try {

				LocalDate date = LocalDate.parse(val.getFechaHora(), formatter2);

				String sql = "update conc_pago2 set pago_bes= ".concat(val.getIdRegion())
						.concat(" where date(fecha_hora)='").concat(date.toString()).concat("' and telefono = '")
						.concat(val.getTelefono()).concat("' and id_concepto=").concat("101").concat(" and id_region= ")
						.concat(val.getIdRegion()).concat(",");

				bw.write(sql);
				bw.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		bw.close();

		return null;
	}

}
