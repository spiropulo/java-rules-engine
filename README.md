# Java Rules Engine

This ultra-lightweight rules engine offers a streamlined computational model that diverges from traditional imperative structures. It promotes a clear, consistent, and easily understood execution strategy, enabling development teams to establish a shared, ubiquitous language for software architecture.

Designed to support engineering teams, this artifact provides a versatile pattern that can be adopted across the enterprise. It remains unopinionated about frameworks and introduces no additional dependencies to the application stack, ensuring seamless integration and flexibility.

## Hello World Example (without conditions)

This is a simple example to show how the EnginePipeline executes EngineActions.

```diff
+ This simple action will be added to our execution pipeline.
+ I'm choosing to use <String> as my generic payload.
```
```java
import com.engine.EngineData;
import com.engine.EngineAction;

public class SampleAction extends EngineAction<String> {
	private String content;
	public SampleAction(String content) {
		this.content = content;
	}
	
	@Override
	public void execute(EngineData<String> pipelineData) {
		System.out.println(this.content);
	}
}
```
```diff
+ Now we put it all together
```
```java
import java.util.Arrays;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineAction;

public class TestHelloWorld {
	@Test
	public void test_helloWorld() throws EngineException {
		new EnginePipeline<String>(Arrays.asList(new SampleAction("Hello World!")), new EngineData<String>()).execute();
	}
}
```
```diff
+ After running this test your output should look something like this:
  Hello World!
```

## Hello World Example (with AND conditions)

This example shows how an EngineAction executes with AND conditions. All of the AND conditions must pass for an action to execute. We can attach an unlimited number of AND conditions to any action (You should consider performance!). We can also pass AND conditions to an ExecutionPipeline. These conditions will be invoked before each action in the pipeline is executed, and they will adhere to the statute of AND conditions execution. 

```diff
+ Here is our sample condition.
```
```java
import com.engine.EngineData;
import com.engine.EngineCondition;

public class SampleCondition implements EngineCondition<String> {
	private boolean result;
	public SampleCondition(boolean result) {
		this.result = result;
	}
	@Override
	public boolean execute(EngineData<String> pipelineData) {
		return this.result;
	}
}
```
We are going to first set the condition to return true, so Hello World! will execute. Then we'll set it to false to prevent execution. Then we'll set back to true and pass a **general** ExecutionPipeline condition, we'll set this condition to false to prevent execution.
```java
import java.util.Arrays;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineAction;

import engine.conditions.SampleCondition;

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
}
```
# Hello World Example (with OR conditions)
This example shows how an EngineAction executes with OR conditions. At least one of the OR conditions must pass for a action to execute. We can attach an unlimited number of OR conditions to any action (You should consider performance!). We can also pass OR conditions to an ExecutionPipeline. These conditions will be invoked before each action in the pipeline is executed, and they will adhere to the statute of OR conditions execution.

```java
import java.util.Arrays;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineAction;

import engine.conditions.SampleCondition;

public class TestHelloWorld {
	@Test
	public void test_helloWorld_or() throws EngineException {
		// First set one condition true and one false...
		EngineAction<String> actionPass = new SampleAction("I have 2 conditions true and false... Hello World!");
		actionPass.addOrCondition(new SampleCondition(true)).addOrCondition(new SampleCondition(true));
		new EnginePipeline<String>(Arrays.asList(actionPass), new EngineData<String>()).execute();
		
		//output: I have 2 conditions true and false... Hello World!

		// Then we set both conditions false
		EngineAction<String> actionFail = new SampleAction("I have 2 conditions false and false... Hello World!");
		actionFail.addOrCondition(new SampleCondition(true)).addOrCondition(new SampleCondition(false));
		new EnginePipeline<String>(Arrays.asList(actionFail), new EngineData<String>()).execute();
		
		//No output.

		// Then we set both conditions false, and we pass a pipeline true condition...
		EngineAction<String> actionFailWithGeneralCondition1 = 
			new SampleAction("I have 2 conditions false and false, with a true pipeline condition... Hello World!");
		actionFailWithGeneralCondition1.addAndCondition(new SampleCondition(true));
		new EnginePipeline<String>(Arrays.asList(actionFailWithGeneralCondition1), 
			Arrays.asList(new SampleCondition(true)), null,
				new EngineData<String>()).execute();
		
		//output: I have 2 conditions false and false, with a true pipeline condition... Hello World!
	}
}
```

## License
[MIT](https://choosealicense.com/licenses/mit/)

