package engine;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineAction;

@SuppressWarnings("unchecked")
public class TestAndOrConditions extends TestParent {

	/**
	 * ***************** AND, OR Conditions *****************
	 */
	@Test
	public void test_FalseOr_TrueAnd_fail() throws EngineException {
		EngineData<String> data = d();
		EngineAction<String> t1 = t1();
		t1.addOrCondition(t()).addAndCondition(f());
		new EnginePipeline<String>(ts(t1), data).execute();
		assert data.get("key1") == null;
	}

	@Test
	public void test_TrueOr_FalseAnd_fail() throws EngineException {
		EngineData<String> data = d();
		EngineAction<String> t1 = t1();
		t1.addOrCondition(f()).addAndCondition(t());
		new EnginePipeline<String>(ts(t1), data).execute();
		assert data.get("key1") == null;
	}

	@Test
	public void test_TrueFalseOrs_TrueAnd_pass() throws EngineException {
		EngineData<String> data = d();
		EngineAction<String> t1 = t1();
		t1.addOrCondition(f()).addOrCondition(t()).addAndCondition(t());
		new EnginePipeline<String>(ts(t1), data).execute();
		assert data.get("key1").equals("one");
	}

	@Test
	public void test_TrueFalseOrs_TrueFalseAnds_fails() throws EngineException {
		EngineData<String> data = d();
		EngineAction<String> t1 = t1();
		t1.addOrCondition(f()).addOrCondition(t()).addAndCondition(t()).addAndCondition(f());
		new EnginePipeline<String>(ts(t1), data).execute();
		assert data.get("key1") == null;
	}

	@Test
	public void test_TrueTrueOrs_TrueFalseAnds_fails() throws EngineException {
		EngineData<String> data = d();
		EngineAction<String> t1 = t1();
		t1.addOrCondition(t()).addOrCondition(t()).addAndCondition(t()).addAndCondition(f());
		new EnginePipeline<String>(ts(t1), data).execute();
		assert data.get("key1") == null;
	}

	@Test
	public void test_allTogether() throws EngineException {
		EngineData<String> data = d();
		EngineAction<String> t1 = t1();
		EngineAction<String> t2 = t2();
		EngineAction<String> t3 = t3();
		t1.addOrCondition(t()).addOrCondition(t()).addAndCondition(t()).addAndCondition(f());
		t2.addOrCondition(t()).addOrCondition(f()).addAndCondition(t());
		t3.addAndCondition(f());
		new EnginePipeline<String>(ts(t1, t2, t3), data).execute();
		assert data.get("key1") == null;
		assert data.get("key2").equals("two");
		assert data.get("key3") == null;
	}

}
