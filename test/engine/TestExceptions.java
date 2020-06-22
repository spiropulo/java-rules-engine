package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineAction;

import engine.actions.SampleAction;

public class TestExceptions {

	/**
	 * ***************** Exceptions *****************
	 */
	@Test
	public void test_EmptyActions_fail() throws EngineException {
		Exception result = null;
		try {
			List<EngineAction<String>> actions = new ArrayList<>();
			new EnginePipeline<String>(actions, new EngineData<String>()).execute();
		} catch (Exception e) {
			result = e;
		} finally {
			assert result instanceof EngineException;
		}
	}

	@Test
	public void test_NullActions_fail() throws EngineException {
		Exception result = null;
		try {
			new EnginePipeline<String>(null, new EngineData<String>()).execute();
		} catch (Exception e) {
			result = e;
		} finally {
			assert result instanceof EngineException;
		}
	}

	@Test
	public void test_NullEngineData_fail() throws EngineException {
		Exception result = null;
		try {
			new EnginePipeline<String>(Arrays.asList(new SampleAction("key1", "one")), null).execute();
		} catch (Exception e) {
			result = e;
		} finally {
			assert result instanceof EngineException;
		}
	}

}
