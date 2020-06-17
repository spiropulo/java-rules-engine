package engine.tasks;

import com.engine.EngineData;
import com.engine.EngineTask;

public class StringTask extends EngineTask<String> {
	private String key;
	private String value;

	public StringTask(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public void execute(EngineData<String> pipelineData) {
		pipelineData.add(this.key, this.value);
	}
}