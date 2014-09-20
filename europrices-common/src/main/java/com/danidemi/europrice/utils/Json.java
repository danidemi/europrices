package com.danidemi.europrice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public String toJson(Object object) throws JsonProcessingException{
		return MAPPER.writeValueAsString(object);
	}
	
}
