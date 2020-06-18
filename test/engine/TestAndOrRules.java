package engine;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineTask;

@SuppressWarnings("unchecked")
public class TestAndOrRules extends TestParent {

	/**
	 * ***************** AND, OR Rules *****************
	 */
	@Test
	public void test_FalseOr_TrueAnd_fail() throws EngineException {
		EngineData<String> data = d();
		EngineTask<String> t1 = t1();
		t1.addOrRule(t()).addAndRule(f());
		new EnginePipeline<String>(ts(t1), data).execute();
		assert data.get("key1") == null;
	}

	@Test
	public void test_TrueOr_FalseAnd_fail() throws EngineException {
		EngineData<String> data = d();
		EngineTask<String> t1 = t1();
		t1.addOrRule(f()).addAndRule(t());
		new EnginePipeline<String>(ts(t1), data).execute();
		assert data.get("key1") == null;
	}

	@Test
	public void test_TrueFalseOrs_TrueAnd_pass() throws EngineException {
		EngineData<String> data = d();
		EngineTask<String> t1 = t1();
		t1.addOrRule(f()).addOrRule(t()).addAndRule(t());
		new EnginePipeline<String>(ts(t1), data).execute();
		assert data.get("key1").equals("one");
	}

	@Test
	public void test_TrueFalseOrs_TrueFalseAnds_fails() throws EngineException {
		EngineData<String> data = d();
		EngineTask<String> t1 = t1();
		t1.addOrRule(f()).addOrRule(t()).addAndRule(t()).addAndRule(f());
		new EnginePipeline<String>(ts(t1), data).execute();
		assert data.get("key1") == null;
	}

	@Test
	public void test_TrueTrueOrs_TrueFalseAnds_fails() throws EngineException {
		EngineData<String> data = d();
		EngineTask<String> t1 = t1();
		t1.addOrRule(t()).addOrRule(t()).addAndRule(t()).addAndRule(f());
		new EnginePipeline<String>(ts(t1), data).execute();
		assert data.get("key1") == null;
	}

	@Test
	public void test_allTogether() throws EngineException {
		EngineData<String> data = d();
		EngineTask<String> t1 = t1();
		EngineTask<String> t2 = t2();
		EngineTask<String> t3 = t3();
		t1.addOrRule(t()).addOrRule(t()).addAndRule(t()).addAndRule(f());
		t2.addOrRule(t()).addOrRule(f()).addAndRule(t());
		t3.addAndRule(f());
		new EnginePipeline<String>(ts(t1, t2, t3), data).execute();
		assert data.get("key1") == null;
		assert data.get("key2").equals("two");
		assert data.get("key3") == null;
	}

}
