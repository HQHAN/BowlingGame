package com.category.sort.hans;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CategorySort {
	
	static class ResultItem {
		JSONObject product;
		ArrayList<String[]> categoryIDAndNames;
		
		public ResultItem(JSONObject p) {
			product = p;
			categoryIDAndNames = new ArrayList<String[]>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// 0. read the json file and create the json obj from it.
		JSONParser parser = new JSONParser();
		
		try {
			JSONObject json = (JSONObject)parser.parse(new FileReader("test.json"));
			
//			JSONArray smCategory = (JSONArray)json.get("smallCategorys");
			JSONArray bgCategory = (JSONArray)json.get("bigCategorys");
			JSONArray products = (JSONArray)json.get("products");
			
			// 1. sort by descending "salesCnt" * "price" 판매액 상위 탑 5
//			Collections.sort(products, new Comparator<JSONObject>() {				
//
//				@Override
//				public int compare(JSONObject o1, JSONObject o2) {
//					int lhs_total_sales = Integer.valueOf((String)o1.get("price")) * Integer.valueOf((String)o1.get("saleCnt"));
//					int rhs_total_sales = Integer.valueOf((String)o2.get("price")) * Integer.valueOf((String)o2.get("saleCnt"));
//					
//					return rhs_total_sales - lhs_total_sales;
//				}
//			});
			
			Iterator<JSONObject> iterator = products.iterator();
			ArrayList<ResultItem> top_five_products = new ArrayList<ResultItem>();
			
			HashMap<Integer, ArrayList<JSONObject>> tempMap = new HashMap<Integer, ArrayList<JSONObject>>();
			
			// configure hashmap using tatalSales as a key and product obj as a value
			while(iterator.hasNext()) {
				JSONObject obj = iterator.next();
				
				int totalSales = Integer.valueOf((String)obj.get("price")) 
						     		* Integer.valueOf((String)obj.get("saleCnt"));
				
				
				if(tempMap.containsKey(totalSales)) {
					ArrayList<JSONObject> list = tempMap.get(totalSales);
					list.add(obj);
				}else {
					ArrayList<JSONObject> list = new ArrayList<JSONObject>();
					list.add(obj);
					tempMap.put(totalSales, list);
				}
				
//				ResultItem item = new ResultItem(obj);
//				System.out.println("i th : " +i + "     ###" + obj.toJSONString());
//				top_five_products.add(item);
			}
			
			// sort the key by descending
			Object[] tempList = tempMap.keySet().toArray();
			Arrays.sort(tempList, new Comparator<Object>(){
				
				@Override
				public int compare(Object o1, Object o2) {
					int lhs = (Integer)o1;
					int rhs = (Integer)o2;
					
					return rhs - lhs;
				}
				
			});
			
			int i = 0;
			for(Object key : tempList) {
				if(i == 5)
					break;
				
				ArrayList<JSONObject> list = tempMap.get(key);
				for(JSONObject o : list) {
					ResultItem item = new ResultItem(o);
					top_five_products.add(item);
				}
				
				i++;
			}
			
			// brute-force -_-
			Iterator<JSONObject> bgIterator = bgCategory.iterator();
			while(bgIterator.hasNext()) {
				JSONObject bgItem = bgIterator.next();
				JSONArray smCat = (JSONArray)bgItem.get("subCategorys");
				
				Iterator<String> smCatIterator = smCat.iterator();
				while(smCatIterator.hasNext()) {
					String s = smCatIterator.next();
					
					for(ResultItem item : top_five_products) {
						if(s.equals(item.product.get("smallCategoryId"))) {
							String[] idAndName = new String[2];
							
							idAndName[0] = (String)bgItem.get("cateId");
							idAndName[1] = (String)bgItem.get("cateName");
							item.categoryIDAndNames.add(idAndName);
						}
					}
				}
			}
			
			for(ResultItem item : top_five_products) {
				
				if(item.categoryIDAndNames.size() == 0) {
					System.out.println("There is no available Big category !");
				}
				
				for(String[] catId: item.categoryIDAndNames) {
					System.out.print("Big Category ID: " + catId[0] +"    " );
					System.out.println("Big Category Name: " + catId[1]  );
				}
				
				System.out.println("Product details :" + item.product.toString() );
				System.out.println("Total Prices :" + Integer.valueOf((String)item.product.get("price")) * Integer.valueOf((String)item.product.get("saleCnt")));
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
