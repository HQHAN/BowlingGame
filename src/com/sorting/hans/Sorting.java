package com.sorting.hans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Sorting {
	
	// input data from the webps004
	private static int[][] mInput = {{110,10}, {101,9}, {101,8}, {103,7}, {105,6}, {105,5},
							  {101,4}, {103,3}, {103,2}, {110,1}};
	
	public static void main(String[] args) {
		
		// 0. Declare HashMap and init with the input values
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		
		Integer key;
		Integer value;
		for(int i=0; i<mInput.length; i++) {
			key = mInput[i][0];
			value = mInput[i][1];
			
			if(map.containsKey(key)) {
				ArrayList<Integer> list = map.get(key);
				list.add(value);
			} else {
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(value);
				map.put(key, list);
			}
		}
		
		// 1. sort the data by ascending order on each key and value
		for(ArrayList<Integer> listValue : map.values()) {
			Collections.sort(listValue); // default sort order Ascending
		}
		
		// 1.1 since we need to print out with key being ordered by ascending, i need to have ordered key lists
		List<Integer> keyList = new ArrayList<Integer>();
		keyList.addAll(map.keySet());
		Collections.sort(keyList);
		
		// 2. print the result with the format specified in problem like "deal id->option ids"
		for(Integer sortedKey : keyList) {
			System.out.print(sortedKey + "->");
			
			ArrayList<Integer> sortedList = map.get(sortedKey);
			for(int i=0; i<sortedList.size(); i++) {
				System.out.print(sortedList.get(i));
				
				if(i == sortedList.size() -1 )
					continue;
				
				System.out.print(",");
			}
			System.out.println();
		}
	}
	
	

}
