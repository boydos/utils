package com.ds.json;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ds.utils.DateUtil;
import com.ds.utils.New;

public class JsonModel implements java.io.Serializable{
	private HashMap<String,Object> paraMap = new HashMap<String,Object>();
	private String needGeneratedId = null;
    private static final String XML_DESC = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    public JsonModel(String jsonString){
    	this.fromJson(jsonString);
    }
    public JsonModel(){
    }
	public String getString(String name){
		Object value = getMapValue(name);
		if(value == null)return null;
		return value.toString();
	}
	public Long getLong(String name){
		Object value = getMapValue(name);
		if(value == null)return null;
		try {
			if(value instanceof Long){
				return (Long)value;
			}
			return new Long(value.toString());
		} catch (Exception e) {
			return null;
		}
	}
	public Long getLong(String name, long def){
		Long val = getLong(name);
		if(val == null)return def;
		return val;
	}
	public Integer getInt(String name){
		Object value = getMapValue(name);
		if(value == null)return null;
		try {
			if(value instanceof Integer){
				return (Integer)value;
			}
			return new Integer(value.toString());
		} catch (Exception e) {
			return null;
		}

	}
	public Integer getInt(String name, int def){
		Integer val = getInt(name);
		if(val == null)return def;
		return val;
	}
	public Double getDouble(String name){
		Object value = getMapValue(name);
		if(value == null)return null;
		try {
			if(value instanceof Double){
				return (Double)value;
			}
			return new Double(value.toString());
		} catch (Exception e) {
			return null;
		}
	}
	public Double getDouble(String name, double def){
		Double val = getDouble(name);
		if(val == null)return def;
		return val;
	}
	
	public Date getDate(String name){
		Object value = getMapValue(name);
		if(value == null)return null;
		if(value instanceof Date){
			return (Date)value;
		}else if(value instanceof Timestamp){
			return DateUtil.timestamp2Utildate((Timestamp)value);
		}
		
		return DateUtil.string2TimeDate(value.toString());
		
		
	}
	public boolean getBoolean(String confname){
		return "true".equalsIgnoreCase(getString(confname));
	}
	public void setString(String name,String value){
		if(value == null)return;
        String mapKey = getMapKey(name);
        if(mapKey != null){
            name = mapKey;
        }
		paraMap.put(name, value);
	}
	public void setDouble(String name,Double value){
		if(value == null)return;
        String mapKey = getMapKey(name);
        if(mapKey != null){
            name = mapKey;
        }
		paraMap.put(name, value);
	}
	public void setLong(String name,Long value){
		if(value == null)return;
        String mapKey = getMapKey(name);
        if(mapKey != null){
            name = mapKey;
        }
		paraMap.put(name, value);
	}
	public void setInt(String name,Integer value){
		if(value == null)return;
        String mapKey = getMapKey(name);
        if(mapKey != null){
            name = mapKey;
        }
		paraMap.put(name, value);
	}
	public void setDate(String name,Date value){
		if(value == null)return;
        String mapKey = getMapKey(name);
        if(mapKey != null){
            name = mapKey;
        }
		paraMap.put(name, value);
	}
	public void set(String name,Object value){
		if(value == null)return;
        String mapKey = getMapKey(name);
        if(mapKey != null){
            name = mapKey;
        }
		paraMap.put(name, value);
	}
	public void remove(String name){
        Set<Map.Entry<String,Object>> entrys = paraMap.entrySet();
        for(Map.Entry<String,Object> entry : entrys){
            String key = entry.getKey();
            if(key.equalsIgnoreCase(name)){
                name = key;
                break;
            }
        }
		paraMap.remove(name);
	}
	public Object get(String name){
		Object value = getMapValue(name);
		return value;
	}
    private String getMapKey(String name){
        Set<Map.Entry<String,Object>> entrys = paraMap.entrySet();
        for(Map.Entry<String,Object> entry : entrys){
            String key = entry.getKey();
            if(key.equalsIgnoreCase(name)){
                return key;
            }
        }
        return null;
    }
    private Object getMapValue(String name){
        Set<Map.Entry<String,Object>> entrys = paraMap.entrySet();
        for(Map.Entry<String,Object> entry : entrys){
            String key = entry.getKey();
            if(key.equalsIgnoreCase(name)){
                return entry.getValue();
            }
        }
        return null;
    }
	public Iterator<String> keys(){
		return paraMap.keySet().iterator();
	}
	public Set<String> keySet(){
		return paraMap.keySet();
	}
	public boolean isEmpty() {
		return keySet().isEmpty();
	}
	public int size() {
		return keySet().size();
	}
	public Map<String,Object> getMap() {
		return paraMap;
	}
	public boolean containsKey(String name){
        Set<Map.Entry<String,Object>> entrys = paraMap.entrySet();
        for(Map.Entry<String,Object> entry : entrys){
            String key = entry.getKey();
            if(key.equalsIgnoreCase(name)){
                return true;
            }
        }
		return false;
	}
	public void clear(){
		paraMap.clear();
	}
	public String toXml(String root){		
		try {
			JSONObject obj = new JSONObject(this, true);
			String xml = XML.toString(obj,root);
			if(root==null)return xml;
			return XML_DESC+xml;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String toJson(){		
		JSONObject obj = new JSONObject(this, true);
		return obj.toString();
	}
	public void fromXML(String xmlString){
		try {
			JSONObject jo = XML.toJSONObject(xmlString);
			parse(this, jo);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		
	}
	protected void fromJson(String jsonString){
		try {
			JSONObject jo = new JSONObject(jsonString);
			parse(this, jo);
		} catch (JSONException e) {
//			e.printStackTrace();
		}
		
	}
	private void parse(JsonModel result,JSONObject jsonObject){
		try {
		JsonModel sobj = null,robj = null;
		JSONArray jarray=null;
		List<Object> list = null;
		Object item,obj = null;
		String key=null;
			Iterator it = jsonObject.keys();
			while(it.hasNext()){
				key = (String)it.next();
				obj = jsonObject.get(key);
				if(obj instanceof JSONObject){
					sobj = new JsonModel();
					result.set(key, sobj);
					parse(sobj,(JSONObject)obj);
				}else if(obj instanceof JSONArray){
					jarray = (JSONArray)obj;
					list = new ArrayList();
					result.set(key, list);
					for(int i=0;i<jarray.length();i++){
						//JSONObject item = jarray.getJSONObject(i);
						item  = jarray.get(i);
						if(item instanceof JSONObject){
							robj = new JsonModel();
							parse(robj,(JSONObject)item);
							list.add(robj);
						}else{
							list.add(item);
						}
						
					}
				}else{
					result.set(key, obj);
				}
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
    public void changeKey(String oldKey, String newKey){
        Object value = getMapValue(oldKey);
        if(value != null){
            this.remove(oldKey);
            set(newKey, value);
        }
    }
	public JsonModel getModel(String name){
		Object value = getMapValue(name);
		if(value == null)return null;
		return (JsonModel)value;
	}
	@SuppressWarnings("unchecked")
	public List<JsonModel> getList(String name){
		Object value =  getMapValue(name);
		if(value == null)return null;
		return (List<JsonModel>)value;
	}	
	@SuppressWarnings("unchecked")
	public List<JsonModel> getList2(String name){
		Object value = getMapValue(name);
		if(value == null)return new ArrayList<JsonModel>();
		return (List<JsonModel>)value;
	}	
	public List<JsonModel> getList(String name,String itemName){
		JsonModel listObj = getModel(name);
		if(listObj == null)return new ArrayList<JsonModel>();
		return listObj.getList(itemName);
	}
	@SuppressWarnings("unchecked")
	public List<String> getStringList(String name){
		Object value =  getMapValue(name);
		if(value == null)return new ArrayList<String>();
		return (List<String>)value;
	}	
	public List<String> getStringList(String name,String itemName){
		JsonModel listObj = getModel(name);
		if(listObj == null)return new ArrayList<String>();
		return listObj.getStringList(itemName);
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getIntegerList(String name){
		Object value =  getMapValue(name);
		if(value == null)return new ArrayList<Integer>();
		return (List<Integer>)value;
	}
	public List<Integer> getIntegerList(String name,String itemName){
		JsonModel listObj = getModel(name);
		if(listObj == null)return new ArrayList<Integer>();
		return listObj.getIntegerList(itemName);
	}
	public boolean isNeedGeneratedId() {
		return (needGeneratedId!=null);
	}
	public String getNeedGeneratedId() {
		return needGeneratedId;
	}
	public void setNeedGeneratedId(String needGeneratedId) {
		this.needGeneratedId = needGeneratedId;
	}
	@Override
	public String toString() {
		return toJson();
	}


    public static String toJsonString(List<?> list){
        JSONArray jarray = new JSONArray(list);
        return jarray.toString();
    }
    public static List<Object> fromJsonString(String json){
        try {
            List<Object> list = New.arrayList();
            JSONArray jarray = new JSONArray(json);
            return JsonArray2List(jarray);
        } catch (JSONException e) {
//            e.printStackTrace();
            return New.arrayList();
        }
    }
    private static List<Object> JsonArray2List(JSONArray jarray)throws JSONException{
        List<Object> list = New.arrayList();
        int cnt = jarray.length();
        for(int i=0;i<cnt;i++){
            Object obj = jarray.get(i);
            if(obj instanceof JSONArray){
                list.add(JsonArray2List((JSONArray)obj));
            }else{
                list.add(jarray.get(i));
            }

        }
        return list;
    }
}
