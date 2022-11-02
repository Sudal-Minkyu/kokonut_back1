package com.app.kokonut.woody.common.component;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataTables {
	
	private Logger logger = LoggerFactory.getLogger(DataTables.class);
	
	private long total;
	private HashMap<String, Object> rows;
	private int pageNumber;
	private int pageSize;
	private HashMap<String, Object> search;

	public DataTables(HashMap<String, Object> searchData, HashMap<String, Object> rows, Long total) {
		this.total=total;
		this.search = searchData;
		this.rows = rows;
	}
	
	public String getJsonString() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("total", this.total);
		map.put("rows", this.rows);
		map.put("pageNumber", this.pageNumber);
		map.put("pageSize", this.pageSize);
		map.put("search", this.search);
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.writeValueAsString(map);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return null;
	}

}
