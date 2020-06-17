package com.engine;

import java.util.HashMap;
import java.util.Map;

public class EngineData<T> {
	Map<String, T> data;

	public EngineData() {
		this.data = new HashMap<String, T>();
	}
	
	public EngineData<T> add(String key, T value) {
		data.put(key, value);
		return this;
	}
	
	public T get(String key) {
		return data.get(key);
	}
}
