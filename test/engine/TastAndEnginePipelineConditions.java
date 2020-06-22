package engine;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineAction;

@SuppressWarnings("unchecked")
public class TastAndEnginePipelineConditions extends TestParent {
	/**
	 * ***************** General AND Conditions *****************
	 */

	@Test
	public void test_engine_pipeline_False_fail() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1()), null, rs(f()), data).execute();
		assert data.get("key1") != "one";
	}

	@Test
	public void test_engine_pipeline_TrueFalse_fail() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1()), null, rs(f(), t()), data).execute();
		assert data.get("key1") != "one";
	}

	@Test
	public void test_engine_pipeline_True_pass() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1()), null, rs(t()), data).execute();
		assert data.get("key1") == "one";
	}

	@Test
	public void test_engine_pipeline_TrueFalse_2actions_fail() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1(), t2()), null, rs(f(), t()), data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
	}

	@Test
	public void test_engine_pipeline_pass() throws EngineException {
		EngineData<String> data = d();
		new EnginePipeline<String>(ts(t1(), t2(), t3()), rs(f()), rs(t()), data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
		assert data.get("key3") != "three";
	}

	@Test
	public void test_engine_pipeline_fail_pass_fail() throws EngineException {
		EngineData<String> data = d();
		EngineAction<String> t2 = t2();
		t2.addOrCondition(t());
		new EnginePipeline<String>(ts(t1(), t2, t3()), rs(f()), rs(t()), data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") == "two";
		assert data.get("key3") != "three";
	}

	@Test
	public void test_engine_pipeline_fail_pass_pass() throws EngineException {
		EngineData<String> data = d();
		EngineAction<String> t2 = t2().addOrCondition(t());
		EngineAction<String> t3 = t3().addOrCondition(t());
		new EnginePipeline<String>(ts(t1(), t2, t3), rs(f()), rs(t()), data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") == "two";
		assert data.get("key3") == "three";
	}
	
	@Test
	public void test_engine_pipeline_fail_fail_pass() throws EngineException {
		EngineData<String> data = d();
		EngineAction<String> t1 = t1().addOrCondition(t()).addAndCondition(f());
		EngineAction<String> t2 = t2().addOrCondition(t()).addAndCondition(f());
		EngineAction<String> t3 = t3().addOrCondition(t());
		new EnginePipeline<String>(ts(t1, t2, t3), rs(f()), rs(t()), data).execute();
		assert data.get("key1") != "one";
		assert data.get("key2") != "two";
		assert data.get("key3") == "three";
	}

}
