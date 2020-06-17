package engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineTask;

import engine.tasks.StringTask;

public class TestExceptions {

	/**
	 * ***************** Exceptions *****************
	 */
	@Test
	public void test_EmptyTasks_fail() throws EngineException {
		Exception result = null;
		try {
			List<EngineTask<String>> tasks = new ArrayList<>();
			new EnginePipeline<String>(tasks, new EngineData<String>()).execute();
		} catch (Exception e) {
			result = e;
		} finally {
			assert result instanceof EngineException;
		}
	}

	@Test
	public void test_NullTasks_fail() throws EngineException {
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
			new EnginePipeline<String>(Arrays.asList(new StringTask("key1", "one")), null).execute();
		} catch (Exception e) {
			result = e;
		} finally {
			assert result instanceof EngineException;
		}
	}

}
