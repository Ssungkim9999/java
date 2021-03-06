package common;

import java.io.*;
import java.util.*;
import com.google.gson.*;

public class PropertyService {
	/**
	 * @author Ssungkim9999
	 * @param file
	 * @param key
	 * @return String value that is property value on key from file(on parameter).properties file.
	*/
	public static String getProperty(String file, String key) {
	  Properties properties = new Properties();
	  try {
	    String path = CommonService.class.getResource("/").getPath()+"/properties/"+file+".properties";
	    properties.load(new FileInputStream(path));
	  }catch (Exception e) {
	    LoggingService.error("CommonService".getClass(), "Exception for get "+key+" from "+file+".properties.", e);
	  }
	  return properties.getProperty(key);
	}

	/**
	 * @author Ssungkim9999
	 * @param file
	 * @param key
	 * @return List<String> contains String value that is split(by "#") data got from file(on parameter).properties file.
	*/
	public static List<String> getPropertyToListByString(String file, String key){
	  List<String> dataList = new ArrayList<>();
	  String data = getProperty(file, key);
	  String[] dataAr = data.split("#");
	  int dataCnt = dataAr == null ? 0 : dataAr.length;
	  for(int i=0; i<dataCnt; i++) dataList.add(dataAr[i]);
	  return dataList;
	}

	/**
	 * @author Ssungkim9999
	 * @param file
	 * @param key
	 * @return List<DataMap> contains data that is got from file(on parameter).properties file.
	 * @explain DataMap that was extends HashMap<String, Object>
	*/
	public static List<DataMap> getPropertyToList(String file, String key) {
	  List<DataMap> dataList = new ArrayList<>();
	  try {
	    JsonParser parser = new JsonParser();
	    JsonObject jsonObject = (JsonObject) parser.parse(new FileReader(CommonService.class.getResource("/").getPath()+"/properties/"+file+".properties"));
	    JsonArray jsonArray = jsonObject.get(key).getAsJsonArray();
	    int jsonSize = jsonArray.size();
	    for(int i=0; i<jsonSize; i++) {
	      JsonObject jo = jsonArray.get(i).getAsJsonObject();
	      Set<String> keySet = jo.keySet();
	      Iterator<String> it = keySet.iterator();
	      DataMap map = new DataMap();
	      while(it.hasNext()) {
	        String jsonKey = it.next();
	        String jsonValue = jo.get(jsonKey).getAsString();
	        map.put(jsonKey, jsonValue);
	      }
	      dataList.add(map);
	    }
	  } catch(Exception e) {
	    LoggingService.error("CommonService".getClass(), "Exception for make List<DataMap> contains DataMap that is data from "+file+".properties.", e);
	  }
	  return dataList;
	}
}
