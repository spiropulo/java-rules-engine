package engine;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineTask;

@SuppressWarnings("unchecked")
public class TastAndGeneralRules extends TestParent {
	/**
	 * ***************** General AND Rules *****************
	 */

	@Test
	public void test_general_False_fail() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1()), null, rs(f()), data).execute();
		assert data.get("key1") != "one";
	}

	@Test
	public void test_general_TrueFalse_fail() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1()), null, rs(f(), t()), data).execute();
		assert data.get("key1") != "one";
	}

	@Test
	public void test_general_True_pass() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1()), null, rs(t()), data).execute();
		assert data.get("key1") == "one";
	}

	@Test
	public void test_general_TrueFalse_2tasks_fail() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1(), t2()), null, rs(f(), t()), data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
	}

	@Test
	public void test_general_pass() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1(), t2(), t3()), rs(f()), rs(t()), data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
		assert data.get("key3") != "three";
	}

	@Test
	public void test_general_fail_pass_fail() throws EngineException {
		EngineData<String> data = d();
		EngineTask<String> t2 = t2();
		t2.addOrRule(t());
		new EnginePipeline<String>(ts(t1(), t2, t3()), rs(f()), rs(t()), data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") == "two";
		assert data.get("key3") != "three";
	}

	@Test
	public void test_general_fail_pass_pass() throws EngineException {
		EngineData<String> data = d();
		EngineTask<String> t2 = t2().addOrRule(t());
		EngineTask<String> t3 = t3().addOrRule(t());
		new EnginePipeline<String>(ts(t1(), t2, t3), rs(f()), rs(t()), data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") == "two";
		assert data.get("key3") == "three";
	}
	
	@Test
	public void test_general_fail_fail_pass() throws EngineException {
		EngineData<String> data = d();
		EngineTask<String> t1 = t1().addOrRule(t()).addAndRule(f());
		EngineTask<String> t2 = t2().addOrRule(t()).addAndRule(f());
		EngineTask<String> t3 = t3().addOrRule(t());
		new EnginePipeline<String>(ts(t1, t2, t3), rs(f()), rs(t()), data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
		assert data.get("key3") == "three";
	}

}
