package engine.rules;

import com.engine.EngineData;
import com.engine.EngineRule;

public class TestRule implements EngineRule<String> {
	private boolean result;

	public TestRule(boolean result) {
		this.result = result;
	}

	@Override
	public boolean execute(EngineData<String> pipelineData) {
		return this.result;
	}

}
