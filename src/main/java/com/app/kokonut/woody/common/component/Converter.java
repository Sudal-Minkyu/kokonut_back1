package com.app.kokonut.woody.common.component;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Converter {
	public static String ObjectToJsonString(Object obj) throws JsonProcessingException{
		ObjectMapper objMapper = new ObjectMapper();
		String jsonString = objMapper.writeValueAsString(obj);
		return jsonString;
	}
	
	public static <T> T JsonStringToObject(Class<T> classType, String jsonString) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper objMapper = new ObjectMapper();
		T obj = objMapper.readValue(jsonString, classType);
		return obj;
	}
}