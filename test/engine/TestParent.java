package engine;

import java.util.ArrayList;
import java.util.List;

import com.engine.EngineData;
import com.engine.EngineRule;
import com.engine.EngineTask;

import engine.rules.SampleRule;
import engine.tasks.SampleTask;

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
		return new SampleRule(false);
	}

	protected EngineRule<String> t() {
		return new SampleRule(true);
	}

	protected EngineTask<String> t1() {
		return new SampleTask("key1", "one");
	}

	protected EngineTask<String> t2() {
		return new SampleTask("key2", "two");
	}

	protected EngineTask<String> t3() {
		return new SampleTask("key3", "three");
	}
	
}
