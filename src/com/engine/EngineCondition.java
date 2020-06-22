package com.engine;

public interface EngineCondition<T> {
	public boolean execute(EngineData<T> pipelineData);
}
