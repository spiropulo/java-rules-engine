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

import engine.rules.TestRule;
import engine.tasks.StringTask;

public class TestOrRules {

	/**
	 * ***************** Or Rules *****************
	 */
	@Test
	public void test_FalseOrRule_fail() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new TestRule(false));
		tasks.add(new StringTask("key1", "one").addOrRules(asList));
		tasks.add(new StringTask("key2", "two").addOrRules(asList));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(tasks, data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
	}

	@Test
	public void test_TrueOrRule_pass() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new TestRule(true));
		tasks.add(new StringTask("key1", "one").addOrRules(asList));
		tasks.add(new StringTask("key2", "two").addOrRules(asList));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(tasks, data).execute();
		assert data.get("key1") == "one";
		assert data.get("key2") == "two";
	}

	@Test
	public void test_TrueFalseOrs_pass() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new TestRule(true), new TestRule(false));
		tasks.add(new StringTask("key1", "one").addOrRules(asList));
		tasks.add(new StringTask("key2", "two").addOrRules(asList));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(tasks, data).execute();
		assert data.get("key1") == "one";
		assert data.get("key2") == "two";
	}

	@Test
	public void test_FalseFalseOrs_fail() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new TestRule(false), new TestRule(false));
		tasks.add(new StringTask("key1", "one").addOrRules(asList));
		tasks.add(new StringTask("key2", "two").addOrRules(asList));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(tasks, data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
	}

	@Test
	public void test_FalseFalse_FalseTrue_Ors_Rule_2tasks_fail_pass() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList1 = Arrays.asList(new TestRule(false), new TestRule(false));
		List<EngineRule<String>> asList2 = Arrays.asList(new TestRule(true), new TestRule(false));
		tasks.add(new StringTask("key1", "one").addOrRules(asList1));
		tasks.add(new StringTask("key2", "two").addOrRules(asList2));
		EngineData<String> data = new EngineData<String>();
		new EnginePipeline<String>(tasks, data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") == "two";
	}

}
