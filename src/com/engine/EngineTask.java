package com.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class EngineTask<T> {
	private List<EngineRule<T>> andRules;
	private List<EngineRule<T>> orRules;
	
	public EngineTask() {
		andRules = new ArrayList<>();
		orRules = new ArrayList<>();
	}

	public EngineTask<T> addAndRules(List<EngineRule<T>> rules) {
		this.andRules.addAll(rules);
		return this;
	}

	public EngineTask<T> addOrRules(List<EngineRule<T>> rules) {
		this.orRules.addAll(rules);
		return this;
	}

	public EngineTask<T> addAndRule(EngineRule<T> rule) {
		this.andRules.add(rule);
		return this;
	}

	public EngineTask<T> addOrRule(EngineRule<T> rule) {
		this.orRules.add(rule);
		return this;
	}

	public List<EngineRule<T>> getAndRules() {
		return this.andRules;
	}

	public List<EngineRule<T>> getOrRules() {
		return this.orRules;
	}
	
	public abstract void execute(EngineData<T> pipelineData);
}
