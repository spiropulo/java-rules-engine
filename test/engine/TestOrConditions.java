package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineCondition;
import com.engine.EngineAction;

import engine.conditions.SampleCondition;
import engine.actions.SampleAction;

public class TestOrConditions {

	/**
	 * ***************** Or Conditions *****************
	 */
	@Test
	public void test_FalseOrCondition_fail() throws EngineException {
		List<EngineAction<String>> actions = new ArrayList<>();
		List<EngineCondition<String>> asList = Arrays.asList(new SampleCondition(false));
		actions.add(new SampleAction("key1", "one").addOrConditions(asList));
		actions.add(new SampleAction("key2", "two").addOrConditions(asList));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(actions, data).execute();
		assert data.get("key1") == null;
		assert data.get("key2") == null;
	}

	@Test
	public void test_TrueOrCondition_pass() throws EngineException {
		List<EngineAction<String>> actions = new ArrayList<>();
		List<EngineCondition<String>> asList = Arrays.asList(new SampleCondition(true));
		actions.add(new SampleAction("key1", "one").addOrConditions(asList));
		actions.add(new SampleAction("key2", "two").addOrConditions(asList));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(actions, data).execute();
		assert data.get("key1").equals("one");
		assert data.get("key2").equals("two");
	}

	@Test
	public void test_TrueFalseOrs_pass() throws EngineException {
		List<EngineAction<String>> actions = new ArrayList<>();
		List<EngineCondition<String>> asList = Arrays.asList(new SampleCondition(true), new SampleCondition(false));
		actions.add(new SampleAction("key1", "one").addOrConditions(asList));
		actions.add(new SampleAction("key2", "two").addOrConditions(asList));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(actions, data).execute();
		assert data.get("key1").equals("one");
		assert data.get("key2").equals("two");
	}

	@Test
	public void test_FalseFalseOrs_fail() throws EngineException {
		List<EngineAction<String>> actions = new ArrayList<>();
		List<EngineCondition<String>> asList = Arrays.asList(new SampleCondition(false), new SampleCondition(false));
		actions.add(new SampleAction("key1", "one").addOrConditions(asList));
		actions.add(new SampleAction("key2", "two").addOrConditions(asList));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(actions, data).execute();
		assert data.get("key1") == null;
		assert data.get("key2") == null;
	}

	@Test
	public void test_FalseFalse_FalseTrue_Ors_Condition_2actions_fail_pass() throws EngineException {
		List<EngineAction<String>> actions = new ArrayList<>();
		List<EngineCondition<String>> asList1 = Arrays.asList(new SampleCondition(false), new SampleCondition(false));
		List<EngineCondition<String>> asList2 = Arrays.asList(new SampleCondition(true), new SampleCondition(false));
		actions.add(new SampleAction("key1", "one").addOrConditions(asList1));
		actions.add(new SampleAction("key2", "two").addOrConditions(asList2));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(actions, data).execute();
		assert data.get("key1") == null;
		assert data.get("key2").equals("two");
	}

}
