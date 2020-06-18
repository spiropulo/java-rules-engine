package sample;

import com.engine.EngineData;
import com.engine.EngineTask;

public class SampleTask extends EngineTask<String> {
	private String content;
	public SampleTask(String content) {
		this.content = content;
	}
	
	@Override
	public void execute(EngineData<String> pipelineData) {
		System.out.println(this.content);
	}
}
