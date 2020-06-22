package engine.conditions;

import com.engine.EngineData;
import com.engine.EngineCondition;

public class SampleCondition implements EngineCondition<String> {
	private boolean result;

	public SampleCondition(boolean result) {
		this.result = result;
	}

	@Override
	public boolean execute(EngineData<String> pipelineData) {
		return this.result;
	}

}
