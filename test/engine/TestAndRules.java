package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineRule;
import com.engine.EngineTask;

import engine.rules.SampleRule;
import engine.tasks.SampleTask;

public class TestAndRules {

	/**
	 * ***************** And Rules *****************
	 */
	@Test
	public void test_FalseAndRule_fail() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new SampleRule(false));
		tasks.add(new SampleTask("key1", "one").addAndRules(asList));
		tasks.add(new SampleTask("key2", "two").addAndRules(asList));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(tasks, data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
	}

	@Test
	public void test_TrueAndRule_pass() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new SampleRule(true));
		tasks.add(new SampleTask("key1", "one").addAndRules(asList));
		tasks.add(new SampleTask("key2", "two").addAndRules(asList));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(tasks, data).execute();
		assert data.get("key1") == "one";
		assert data.get("key2") == "two";
	}

	@Test
	public void test_TrueFalseAndRule_fail() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new SampleRule(true), new SampleRule(false));
		tasks.add(new SampleTask("key1", "one").addAndRules(asList));
		tasks.add(new SampleTask("key2", "two").addAndRules(asList));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(tasks, data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
	}

	@Test
	public void test_TrueFalseAndRule_2tasks_1pass_2fail() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList1 = Arrays.asList(new SampleRule(true), new SampleRule(true));
		List<EngineRule<String>> asList2 = Arrays.asList(new SampleRule(true), new SampleRule(false));
		tasks.add(new SampleTask("key1", "one").addAndRules(asList1));
		tasks.add(new SampleTask("key2", "two").addAndRules(asList2));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(tasks, data).execute();
		assert data.get("key1") == "one";
		assert data.get("key2") != "two";
	}

}
