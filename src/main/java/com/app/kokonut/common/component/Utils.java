package com.app.kokonut.common.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
//	public static String getToday()
//	{
//		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
//		return sd.format(new Date());
//	}
//
//	public static String getYesterday()
//	{
//		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar cDateCal = Calendar.getInstance();
//		cDateCal.add(Calendar.DATE, -1);
//		Date date = cDateCal.getTime();
//		sd.format( date );
//		return sd.format( date );
//	}
//
//	@SuppressWarnings("unchecked")
//	public static JSONArray convertListToJson(List<HashMap<String, Object>> listMap) {
//		JSONArray jsonArray = new JSONArray();
//		for(HashMap<String, Object> map : listMap) {
//			jsonArray.add(convertMapToJson(map));
//		}
//
//		return jsonArray;
//	}
//
//	@SuppressWarnings("unchecked")
//	public static JSONObject convertMapToJson(HashMap<String, Object> map) {
//		JSONObject json = new JSONObject();
//		for (Entry<String, Object> entry : map.entrySet()) {
//			String key = entry.getKey();
//			Object value = entry.getValue();
//			json.put(key, value);
//		}
//
//		return json;
//	}
//
	public static String getClientIp(HttpServletRequest request) {
		String userIp = request.getHeader("X-Forwarded-For");

		if (userIp == null || userIp.length() == 0 || "unknown".equalsIgnoreCase(userIp)) {
			userIp = request.getHeader("Proxy-Client-IP");
		}

		if (userIp == null || userIp.length() == 0 || "unknown".equalsIgnoreCase(userIp)) {
			userIp = request.getHeader("WL-Proxy-Client-IP");
		}

		if (userIp == null || userIp.length() == 0 || "unknown".equalsIgnoreCase(userIp)) {
			userIp = request.getHeader("HTTP_CLIENT_IP");
		}

		if (userIp == null || userIp.length() == 0 || "unknown".equalsIgnoreCase(userIp)) {
			userIp = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (userIp == null || userIp.length() == 0 || "unknown".equalsIgnoreCase(userIp)) {
			userIp = request.getRemoteAddr();
		}

		return userIp;
	}
//
//	public static boolean isNumeric(String s) {
//		java.util.regex.Pattern pattern = Pattern.compile("[+-]?\\d+");
//		return pattern.matcher(s).matches();
//	}
//
//
//	public static boolean isSqlInjection(String str) {
//		Pattern specialChars = Pattern.compile("['\"\\-#()@;=*/+]");
//		str = specialChars.matcher(str).replaceAll("");
//		String regex = "(union|select|from|where)";
//		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//		Matcher matcher = pattern.matcher(str);
//		if(matcher.find()) {
//			return true;
//		}
//
//		return false;
//	}

	public static HashMap<String,Object> convertJSONstringToMap(String json){
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> map = new HashMap<>();
		try {
			map = (HashMap<String, Object>) mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}

	public static File convertMultipartFileToFile(MultipartFile mfile) {
		File file = new File(mfile.getOriginalFilename());
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(mfile.getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return file;
	}

	/**
	 * 엑셀 Formula injection 검사
	 * @param contents
	 * @return Formula injection 기능 제거한 contents
	 */
	public static String weekPointForExcel(String contents) {
		Pattern pattern = Pattern.compile("[('='|'@'|'+|'\\-')]cmd");
		Matcher matcher = pattern.matcher(contents);
		if(matcher.find()) {
			return " ".concat(contents);
		} else {
			return contents;
		}
	}

}
