package com.engine;

public interface EngineRule<T> {
	public boolean execute(EngineData<T> pipelineData);
}
