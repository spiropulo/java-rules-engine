package sample;

import java.util.Arrays;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineTask;

import engine.rules.SampleRule;

public class TastHelloWorld {
	@Test
	public void test_helloWorld_and() throws EngineException {
		// First set the rule to true so we see Hello World! in the out put.
		EngineTask<String> taskPass = new SampleTask("I have a true task rule... Hello World!");
		taskPass.addAndRule(new SampleRule(true));
		new EnginePipeline<String>(Arrays.asList(taskPass), new EngineData<String>()).execute();
		
		//output: I have a true task rule... Hello World!

		// Now we set the rule to false to prevent SampleTask from execution.
		EngineTask<String> taskFail = new SampleTask("I have a false rule...");
		taskFail.addAndRule(new SampleRule(false));
		new EnginePipeline<String>(Arrays.asList(taskFail), new EngineData<String>()).execute();
		
		//No output.

		// Now back to true but with a EnginePipeline rule set to false.
		EngineTask<String> taskFailWithGeneralRule1 = new SampleTask("I have a true task rule but a false pipeline rule...");
		taskFailWithGeneralRule1.addAndRule(new SampleRule(true));
		new EnginePipeline<String>(Arrays.asList(taskFailWithGeneralRule1), null, Arrays.asList(new SampleRule(false)),
				new EngineData<String>()).execute();
		
		//No output.

		// Now back to true but with a EnginePipeline rule set to true.
		EngineTask<String> taskFailWithGeneralRule2 = new SampleTask("I have a true task rule and a true pipeline rule... Hello World!");
		taskFailWithGeneralRule2.addAndRule(new SampleRule(true));
		new EnginePipeline<String>(Arrays.asList(taskFailWithGeneralRule2), null, Arrays.asList(new SampleRule(true)),
				new EngineData<String>()).execute();
		
		//output: I have a true task rule and a true pipeline rule... Hello World!
	}
	
	@Test
	public void test_helloWorld_or() throws EngineException {
		// First set one rule true and one false...
		EngineTask<String> taskPass = new SampleTask("I have 2 rules true and false... Hello World!");
		taskPass.addOrRule(new SampleRule(true)).addOrRule(new SampleRule(true));
		new EnginePipeline<String>(Arrays.asList(taskPass), new EngineData<String>()).execute();
		
		//output: I have 2 rules true and false... Hello World!

		// Then we set both rules false
		EngineTask<String> taskFail = new SampleTask("I have 2 rules false and false... Hello World!");
		taskFail.addOrRule(new SampleRule(true)).addOrRule(new SampleRule(false));
		new EnginePipeline<String>(Arrays.asList(taskFail), new EngineData<String>()).execute();
		
		//No output.

		// Then we set both rules false, and we pass a pipeline true rule...
		EngineTask<String> taskFailWithGeneralRule1 = new SampleTask("I have 2 rules false and false, with a true pipeline rule... Hello World!");
		taskFailWithGeneralRule1.addAndRule(new SampleRule(true));
		new EnginePipeline<String>(Arrays.asList(taskFailWithGeneralRule1), Arrays.asList(new SampleRule(true)), null,
				new EngineData<String>()).execute();
		
		//output: I have 2 rules false and false, with a true pipeline rule... Hello World!
	}
}