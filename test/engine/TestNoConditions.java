package engine;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineAction;

import engine.actions.SampleAction;

public class TestNoConditions {

	@Test
	public void test_NoConditions_pass() throws EngineException {
		List<EngineAction<String>> actions = new ArrayList<>();
		actions.add(new SampleAction("key1", "one"));
		actions.add(new SampleAction("key2", "two"));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(actions, data).execute();
		assert data.get("key1").equals("one");
		assert data.get("key2").equals("two");
	}

}
