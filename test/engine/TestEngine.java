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

public class TestEngine {
	EnginePipeline<String> underTest;

	@Test
	public void testNoRulesExecutes() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		tasks.add(new StringTask("key1", "one"));
		tasks.add(new StringTask("key2", "two"));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") == "one";
		assert data.get("key2") == "two";
	}

	@Test
	public void testExceptionIfEmptyTasks() throws EngineException {
		Exception result = null;
		try {
			List<EngineTask<String>> tasks = new ArrayList<>();
			underTest = new EnginePipeline<String>(tasks, new EngineData<String>());
			underTest.execute();
		} catch (Exception e) {
			result = e;
		} finally {
			assert result instanceof EngineException;
		}
	}

	@Test
	public void testExceptionIfNullTasks() throws EngineException {
		Exception result = null;
		try {
			underTest = new EnginePipeline<String>(null, new EngineData<String>());
			underTest.execute();
		} catch (Exception e) {
			result = e;
		} finally {
			assert result instanceof EngineException;
		}
	}

	@Test
	public void testExceptionIfNullEngineData() throws EngineException {
		Exception result = null;
		try {
			underTest = new EnginePipeline<String>(Arrays.asList(new StringTask("key1", "one")), null);
			underTest.execute();
		} catch (Exception e) {
			result = e;
		} finally {
			assert result instanceof EngineException;
		}
	}
	
	/**
	 * And Rules
	 */
	@Test
	public void testOneFalseAndRuleNoExecution() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new TestRule(false));
		tasks.add(new StringTask("key1", "one").addAndRules(asList));
		tasks.add(new StringTask("key2", "two").addAndRules(asList));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
	}

	@Test
	public void testOneTrueAndRuleExecutes() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new TestRule(true));
		tasks.add(new StringTask("key1", "one").addAndRules(asList));
		tasks.add(new StringTask("key2", "two").addAndRules(asList));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") == "one";
		assert data.get("key2") == "two";
	}

	@Test
	public void testOneTrueOneFalseAndRuleNoExecution() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new TestRule(true), new TestRule(false));
		tasks.add(new StringTask("key1", "one").addAndRules(asList));
		tasks.add(new StringTask("key2", "two").addAndRules(asList));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
	}

	@Test
	public void testOneTrueOneFalseAndRule_2tasks_1pass_2fail() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList1 = Arrays.asList(new TestRule(true), new TestRule(true));
		List<EngineRule<String>> asList2 = Arrays.asList(new TestRule(true), new TestRule(false));
		tasks.add(new StringTask("key1", "one").addAndRules(asList1));
		tasks.add(new StringTask("key2", "two").addAndRules(asList2));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") == "one";
		assert data.get("key2") != "two";
	}
	
	/**
	 * Or Rules
	 */
	@Test
	public void testOneFalseOrRuleNoExecution() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new TestRule(false));
		tasks.add(new StringTask("key1", "one").addOrRules(asList));
		tasks.add(new StringTask("key2", "two").addOrRules(asList));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
	}

	@Test
	public void testOneTrueOrRuleExecutes() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new TestRule(true));
		tasks.add(new StringTask("key1", "one").addOrRules(asList));
		tasks.add(new StringTask("key2", "two").addOrRules(asList));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") == "one";
		assert data.get("key2") == "two";
	}

	@Test
	public void test_TrueFalse_Ors_RuleExecutes() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new TestRule(true), new TestRule(false));
		tasks.add(new StringTask("key1", "one").addOrRules(asList));
		tasks.add(new StringTask("key2", "two").addOrRules(asList));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") == "one";
		assert data.get("key2") == "two";
	}

	@Test
	public void test_FalseFalse_Ors_RuleNoExecution() throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		List<EngineRule<String>> asList = Arrays.asList(new TestRule(false), new TestRule(false));
		tasks.add(new StringTask("key1", "one").addOrRules(asList));
		tasks.add(new StringTask("key2", "two").addOrRules(asList));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
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
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") != "one";
		assert data.get("key2") == "two";
	}
	
	/**
	 * And, Or Rules
	 */
	@Test
	public void test_FalseOr_TrueAnd_fails () throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		EngineRule<String> pass = new TestRule(true);
		EngineRule<String> fail = new TestRule(false);
		tasks.add(new StringTask("key1", "one").addOrRule(pass).addAndRule(fail));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") != "one";
	}
	
	/**
	 * And, Or Rules
	 */
	@Test
	public void test_TrueOr_FalseAnd_fails () throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		EngineRule<String> pass = new TestRule(true);
		EngineRule<String> fail = new TestRule(false);
		tasks.add(new StringTask("key1", "one").addOrRule(fail).addAndRule(pass));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") != "one";
	}
	
	/**
	 * And, Or Rules
	 */
	@Test
	public void test_TrueFalseOrs_TrueAnd_pass () throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		EngineRule<String> pass = new TestRule(true);
		EngineRule<String> fail = new TestRule(false);
		tasks.add(new StringTask("key1", "one").addOrRule(fail).addOrRule(pass).addAndRule(pass));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") == "one";
	}
	
	/**
	 * And, Or Rules
	 */
	@Test
	public void test_TrueFalseOrs_TrueFalseAnds_fails () throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		EngineRule<String> pass = new TestRule(true);
		EngineRule<String> fail = new TestRule(false);
		tasks.add(new StringTask("key1", "one").addOrRule(fail).addOrRule(pass).addAndRule(pass).addAndRule(fail));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") != "one";
	}
	
	/**
	 * And, Or Rules
	 */
	@Test
	public void test_TrueTrueOrs_TrueFalseAnds_fails () throws EngineException {
		List<EngineTask<String>> tasks = new ArrayList<>();
		EngineRule<String> pass = new TestRule(true);
		EngineRule<String> fail = new TestRule(false);
		tasks.add(new StringTask("key1", "one").addOrRule(fail).addOrRule(pass).addAndRule(pass).addAndRule(fail));
		EngineData<String> data = new EngineData<String>();
		underTest = new EnginePipeline<String>(tasks, data);
		underTest.execute();
		assert data.get("key1") != "one";
	}
}
