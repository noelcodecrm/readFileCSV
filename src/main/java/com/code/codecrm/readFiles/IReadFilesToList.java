package com.code.codecrm.readFiles;

import java.util.List;

import com.code.codecrm.Pojos.ConcPago;

public interface IReadFilesToList {

	public List<ConcPago> readFile(int positionField, String nameFile) throws Exception;

}
