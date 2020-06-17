package com.engine;

import java.util.List;

public class EnginePipeline<T> {
	private List<EngineTask<T>> tasks;
	private EngineData<T> data;

	public EnginePipeline(List<EngineTask<T>> tasks, EngineData<T> data) throws EngineException {
		if (tasks == null || tasks.size() <= 0) {
			throw new EngineException("At least one task must be provided.");
		}
		this.tasks = tasks;

		if (data == null) {
			throw new EngineException("EngineData cannot be null.");
		} else {
			this.data = data;
		}
	}

	public void execute() {
		this.tasks.forEach(t -> {
			if (this.passRules(t)) {
				t.execute(this.data);
			}
		});

	}

	private boolean passRules(EngineTask<T> task) {
		boolean ands = task.getAndRules().size() == 0 ? true : task.getAndRules().stream().allMatch(r -> r.execute(this.data));
		boolean ors = task.getOrRules().size() == 0 ? true : task.getOrRules().stream().anyMatch(r -> r.execute(this.data));
		return ands && ors; 
	}
}
