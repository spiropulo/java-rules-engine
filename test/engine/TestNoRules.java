package engine;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineTask;

import engine.tasks.SampleTask;

public class TestNoRules {

	@Test
	public void test_NoRules_pass() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		tasks.add(new SampleTask("key1", "one"));
		tasks.add(new SampleTask("key2", "two"));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(tasks, data).execute();
		assert data.get("key1") == "one";
		assert data.get("key2") == "two";
	}

}
