package engine;

import java.util.ArrayList;
import java.util.List;

import com.engine.EngineData;
import com.engine.EngineRule;
import com.engine.EngineTask;

import engine.rules.TestRule;
import engine.tasks.StringTask;

@SuppressWarnings("unchecked")
public class TestParent {

	protected List<EngineRule<String>> rs(EngineRule<String>... rules) {
		List<EngineRule<String>> result = new ArrayList<>();
		for (EngineRule<String> r : rules) {
			result.add(r);
		}

		return result;
	}

	protected List<EngineTask<String>> ts(EngineTask<String>... tasks) {
		List<EngineTask<String>> result = new ArrayList<>();
		for (EngineTask<String> r : tasks) {
			result.add(r);
		}

		return result;
	}

	protected EngineData<String> d() {
		return new EngineData<String>();
	}

	protected EngineRule<String> f() {
		return new TestRule(false);
	}

	protected EngineRule<String> t() {
		return new TestRule(true);
	}

	protected EngineTask<String> t1() {
		return new StringTask("key1", "one");
	}

	protected EngineTask<String> t2() {
		return new StringTask("key2", "two");
	}

	protected EngineTask<String> t3() {
		return new StringTask("key3", "three");
	}
	
}
