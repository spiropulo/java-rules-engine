package sample;

import com.engine.EngineData;
import com.engine.EngineAction;

public class SampleAction extends EngineAction<String> {
	private String content;
	public SampleAction(String content) {
		this.content = content;
	}
	
	@Override
	public void execute(EngineData<String> pipelineData) {
		System.out.println(this.content);
	}
}
