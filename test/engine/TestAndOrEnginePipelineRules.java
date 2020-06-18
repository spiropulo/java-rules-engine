package engine;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineTask;

@SuppressWarnings("unchecked")
public class TestAndOrEnginePipelineRules extends TestParent {
	/**
	 * ***************** General AND, OR Rules *****************
	 */

	@Test
	public void test_engine_pipeline_TrueFalseOr_TrueTrueAnd_2tasks_pass() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1(), t2()), rs(f(), t()), rs(t(), t()), data).execute();
		assert data.get("key1").equals("one");
		assert data.get("key2").equals("two");
	}

	@Test
	public void test_engine_pipeline_FalseFalseOr_TrueTrueAnd_2tasks_fail() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1(), t2()), rs(f(), f()), rs(t(), t()), data).execute();
		assert data.get("key1") == null;
		assert data.get("key2") == null;
	}

	@Test
	public void test_engine_pipeline_TrueTrueOr_TrueFalseAnd_2tasks_fail() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1(), t2()), rs(t(), t()), rs(t(), f()), data).execute();
		assert data.get("key1") == null;
		assert data.get("key2") == null;
	}

	@Test
	public void test_TrueOr_general_FalseOr_TrueAnd_pass() throws EngineException {
		EngineData<String> data = d();
		EngineTask<String> t1 = t1();
		t1.addOrRule(t());
		new EnginePipeline<String>(ts(t1), rs(f()), rs(t()), data).execute();
		assert data.get("key1").equals("one");
	}

	@Test
	public void test_FalseAnd_general_TrueOr_TrueAnd_fail() throws EngineException {
		EngineData<String> data = d();
		EngineTask<String> t1 = t1();
		t1.addAndRule(f());
		new EnginePipeline<String>(ts(t1), rs(t()), rs(t()), data).execute();
		assert data.get("key1") == null;
	}

}
