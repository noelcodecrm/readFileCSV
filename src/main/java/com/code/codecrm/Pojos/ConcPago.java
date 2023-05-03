package com.code.codecrm.Pojos;


import java.math.BigDecimal;

import lombok.Data;

@Data
public class ConcPago {
	
	
	private String idRegion;
	private String telefono;
	private String cuenta;
	private BigDecimal total;
	private String fechaHora;
	private String default5;
}
