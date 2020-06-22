package com.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class EngineAction<T> {
	private List<EngineCondition<T>> andConditions;
	private List<EngineCondition<T>> orConditions;

	public EngineAction() {
		andConditions = new ArrayList<>();
		orConditions = new ArrayList<>();
	}

	public EngineAction<T> addAndConditions(List<EngineCondition<T>> conditions) {
		if (conditions == null || conditions.size() <= 0)
			throw new ActionException("Can't add null/empty AND conditions");
		this.andConditions.addAll(conditions);
		return this;
	}

	public EngineAction<T> addOrConditions(List<EngineCondition<T>> conditions) {
		if (conditions == null || conditions.size() <= 0)
			throw new ActionException("Can't add null/empty OR conditions");
		this.orConditions.addAll(conditions);
		return this;
	}

	public EngineAction<T> addAndCondition(EngineCondition<T> condition) {
		if (condition == null)
			throw new ActionException("Can't add null AND condition");
		this.andConditions.add(condition);
		return this;
	}

	public EngineAction<T> addOrCondition(EngineCondition<T> condition) {
		if (condition == null)
			throw new ActionException("Can't add null OR condition");
		this.orConditions.add(condition);
		return this;
	}

	public List<EngineCondition<T>> getAndConditions() {
		return this.andConditions;
	}

	public List<EngineCondition<T>> getOrConditions() {
		return this.orConditions;
	}

	public abstract void execute(EngineData<T> pipelineData);
}
