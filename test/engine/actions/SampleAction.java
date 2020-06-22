package engine.actions;

import com.engine.EngineData;
import com.engine.EngineAction;

public class SampleAction extends EngineAction<String> {
	private String key;
	private String value;

	public SampleAction(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public void execute(EngineData<String> pipelineData) {
		pipelineData.add(this.key, this.value);
	}
}
