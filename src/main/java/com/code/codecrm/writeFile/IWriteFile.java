package com.code.codecrm.writeFile;

import java.util.List;

import com.code.codecrm.Pojos.ConcPago;

public interface IWriteFile {
	
	public String witeFileFilter(List<ConcPago> resultSequeseFilter) throws Exception;
	public String writeFileSql(List<ConcPago> resultSequeseFilter) throws Exception;
}
