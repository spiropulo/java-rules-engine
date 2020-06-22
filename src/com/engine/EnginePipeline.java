package com.engine;

import java.util.List;

public class EnginePipeline<T> {
	private List<EngineAction<T>> actions;
	private EngineData<T> data;

	public EnginePipeline(List<EngineAction<T>> actions, EngineData<T> data) throws EngineException {
		if (actions == null || actions.size() <= 0) {
			throw new EngineException("At least one action must be provided.");
		}
		this.actions = actions;

		if (data == null) {
			throw new EngineException("EngineData cannot be null.");
		} else {
			this.data = data;
		}
	}

	public EnginePipeline(List<EngineAction<T>> actions, List<EngineCondition<T>> orConditions, List<EngineCondition<T>> andConditions,
			EngineData<T> data) throws EngineException {
		if (actions == null || actions.size() <= 0) {
			throw new EngineException("At least one action must be provided.");
		}
		this.actions = actions;

		if (orConditions != null && orConditions.size() > 0) {
			this.actions.forEach(t -> t.addOrConditions(orConditions));
		}

		if (andConditions != null && andConditions.size() > 0) {
			this.actions.forEach(t -> t.addAndConditions(andConditions));
		}

		if (data == null) {
			throw new EngineException("EngineData cannot be null.");
		} else {
			this.data = data;
		}
	}

	public void execute() {
		this.actions.forEach(t -> {
			if (this.passConditions(t)) {
				t.execute(this.data);
			}
		});

	}

	private boolean passConditions(EngineAction<T> action) {
		boolean ands = action.getAndConditions().size() == 0 ? true
				: action.getAndConditions().stream().allMatch(r -> r.execute(this.data));
		boolean ors = action.getOrConditions().size() == 0 ? true
				: action.getOrConditions().stream().anyMatch(r -> r.execute(this.data));
		return ands && ors;
	}
}
