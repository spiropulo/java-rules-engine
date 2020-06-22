package engine;

import java.util.ArrayList;
import java.util.List;

import com.engine.EngineData;
import com.engine.EngineCondition;
import com.engine.EngineAction;

import engine.conditions.SampleCondition;
import engine.actions.SampleAction;

@SuppressWarnings("unchecked")
public class TestParent {

	protected List<EngineCondition<String>> rs(EngineCondition<String>... conditions) {
		List<EngineCondition<String>> result = new ArrayList<>();
		for (EngineCondition<String> r : conditions) {
			result.add(r);
		}

		return result;
	}

	protected List<EngineAction<String>> ts(EngineAction<String>... actions) {
		List<EngineAction<String>> result = new ArrayList<>();
		for (EngineAction<String> r : actions) {
			result.add(r);
		}

		return result;
	}

	protected EngineData<String> d() {
		return new EngineData<String>();
	}

	protected EngineCondition<String> f() {
		return new SampleCondition(false);
	}

	protected EngineCondition<String> t() {
		return new SampleCondition(true);
	}

	protected EngineAction<String> t1() {
		return new SampleAction("key1", "one");
	}

	protected EngineAction<String> t2() {
		return new SampleAction("key2", "two");
	}

	protected EngineAction<String> t3() {
		return new SampleAction("key3", "three");
	}
	
}
