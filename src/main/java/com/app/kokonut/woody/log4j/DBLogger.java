package com.app.kokonut.woody.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.StructuredDataMessage;

import java.util.UUID;

public class DBLogger {
	private final String MARKER = "LOG4JDB";
	private Logger logger;
	private Marker marker;
	
	public enum LEVEL {
		FATAL, ERROR, WARN, INFO, DEBUG, TRACE
	}
	
	public enum TYPE {
		CREATE, READ, UPDATE, DELETE, DOWNLOAD
	}
	
	public DBLogger(final Class<?> clazz) {
		logger = LogManager.getLogger(clazz);
		marker = MarkerManager.getMarker(MARKER);
	}
	
	public boolean save(LEVEL level, Integer apiKeyIdx, String ip, TYPE type, String messageHeader, String message) {
		if(logger == null) {
			System.out.println("logger is null.");
			return false;
		}
		
		if(marker == null) {
			System.out.println("marker is null.");
			return false;
		}
		
		try {
			String confirm = UUID.randomUUID().toString().replace("-", "");
			StructuredDataMessage msg = new StructuredDataMessage(confirm, "", "");
		    msg.put("apiKeyIdx", apiKeyIdx.toString());
			msg.put("ip", ip);
			msg.put("requestType", type.toString());
			msg.put("message", messageHeader + message);
			
			switch(level) {
				case FATAL:
					logger.fatal(marker, msg);
					break;
				case ERROR:
					logger.error(marker, msg);
					break;
				case WARN:
					logger.warn(marker, msg);
					break;
				case INFO:
					logger.info(marker, msg);
					break;
				case DEBUG:
					logger.debug(marker, msg);
					break;
				case TRACE:
					logger.trace(marker, msg);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
