package sample;

import java.util.Arrays;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;

import engine.conditions.SampleCondition;

import com.engine.EngineAction;

public class TestHelloWorld {
	@Test
	public void test_helloWorld_and() throws EngineException {
		// First set the condition to true so we see Hello World! in the out put.
		EngineAction<String> actionPass = new SampleAction("I have a true action condition... Hello World!");
		actionPass.addAndCondition(new SampleCondition(true));
		new EnginePipeline<String>(Arrays.asList(actionPass), new EngineData<String>()).execute();
		
		//output: I have a true action condition... Hello World!

		// Now we set the condition to false to prevent SampleAction from execution.
		EngineAction<String> actionFail = new SampleAction("I have a false condition...");
		actionFail.addAndCondition(new SampleCondition(false));
		new EnginePipeline<String>(Arrays.asList(actionFail), new EngineData<String>()).execute();
		
		//No output.

		// Now back to true but with a EnginePipeline condition set to false.
		EngineAction<String> actionFailWithGeneralCondition1 = new SampleAction("I have a true action condition but a false pipeline condition...");
		actionFailWithGeneralCondition1.addAndCondition(new SampleCondition(true));
		new EnginePipeline<String>(Arrays.asList(actionFailWithGeneralCondition1), null, Arrays.asList(new SampleCondition(false)),
				new EngineData<String>()).execute();
		
		//No output.

		// Now back to true but with a EnginePipeline condition set to true.
		EngineAction<String> actionFailWithGeneralCondition2 = new SampleAction("I have a true action condition and a true pipeline condition... Hello World!");
		actionFailWithGeneralCondition2.addAndCondition(new SampleCondition(true));
		new EnginePipeline<String>(Arrays.asList(actionFailWithGeneralCondition2), null, Arrays.asList(new SampleCondition(true)),
				new EngineData<String>()).execute();
		
		//output: I have a true action condition and a true pipeline condition... Hello World!
	}
	
	@Test
	public void test_helloWorld_or() throws EngineException {
		// First set one condition true and one false...
		EngineAction<String> actionPass = new SampleAction("I have 2 conditions true and false... Hello World!");
		actionPass.addOrCondition(new SampleCondition(true)).addOrCondition(new SampleCondition(true));
		new EnginePipeline<String>(Arrays.asList(actionPass), new EngineData<String>()).execute();
		
		//output: I have 2 conditions true and false... Hello World!

		// Then we set both conditions false
		EngineAction<String> actionFail = new SampleAction("I have 2 conditions false and false... Hello World!");
		actionFail.addOrCondition(new SampleCondition(false)).addOrCondition(new SampleCondition(false));
		new EnginePipeline<String>(Arrays.asList(actionFail), new EngineData<String>()).execute();
		
		//No output.

		// Then we set both conditions false, and we pass a pipeline true condition...
		EngineAction<String> actionFailWithGeneralCondition1 = new SampleAction("I have 2 conditions false and false, with a true pipeline condition... Hello World!");
		actionFailWithGeneralCondition1.addAndCondition(new SampleCondition(true));
		new EnginePipeline<String>(Arrays.asList(actionFailWithGeneralCondition1), Arrays.asList(new SampleCondition(true)), null,
				new EngineData<String>()).execute();
		
		//output: I have 2 conditions false and false, with a true pipeline condition... Hello World!
	}
}