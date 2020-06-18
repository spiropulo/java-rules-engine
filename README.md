# Java Rules Engine

This ultra-light-weight rules engine provides an **alternative computational** model and not the usual imperative structures. These structures make it easy to follow a consistent and well understood execution strategy. Development teams can then speak in common terms building **ubiquitous language** around software constitution.

The spirit of this artifact is to assist engineering teams with a common pattern that can be leverage across the enterprise. It is **completely** unopinionated, in terms of frameworks. It introduces no new dependencies to an application stack.


## Hello World Example (without rules)

This is a simple example to show how the EnginePipeline executes EngineTasks 
(In some rules engines Tasks are called actions)

```diff
+ This simple task will be added to our execution pipeline.
+ I'm choosing to use <String> as my generic payload.
```
```java
import com.engine.EngineData;
import com.engine.EngineTask;

public class SampleTask extends EngineTask<String> {
	private String content;
	public SampleTask(String content) {
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
import com.engine.EngineTask;

public class TestHelloWorld {
	@Test
	public void test_helloWorld() throws EngineException {
		new EnginePipeline<String>(Arrays.asList(new SampleTask("Hello World!")), new EngineData<String>()).execute();
	}
}
```
```diff
+ After running this test your output should look something like this:
  Hello World!
```

## Hello World Example (with AND rules)

This example shows how an EngineTask executes with AND rules. All of the AND rules must pass for a task to execute. We can attach an unlimited number of AND rules to any task (You should consider performance!). We can also pass AND rules to an ExecutionPipeline. These rules will be invoked before each task in the pipeline is executed, and they will adhere to the statute of AND rules execution. 

```diff
+ Here is our sample rule.
```
```java
import com.engine.EngineData;
import com.engine.EngineRule;

public class SampleRule implements EngineRule<String> {
	private boolean result;
	public SampleRule(boolean result) {
		this.result = result;
	}
	@Override
	public boolean execute(EngineData<String> pipelineData) {
		return this.result;
	}
}
```
We are going to first set the rule to return true, so Hello World! will execute. Then we'll set it to false to prevent execution. Then we'll set back to true and pass a **general** ExecutionPipeline rule, we'll set this rule to false to prevent execution.
```diff
import java.util.Arrays;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineTask;

import engine.rules.SampleRule;

public class TestHelloWorld {
	@Test
	public void test_helloWorld_and() throws EngineException {
-		// First set the rule to true so we see Hello World! in the out put.
		EngineTask<String> taskPass = new SampleTask("I have a true task rule... Hello World!");
		taskPass.addAndRule(new SampleRule(true));
		new EnginePipeline<String>(Arrays.asList(taskPass), new EngineData<String>()).execute();
		
+		//output: I have a true task rule... Hello World!

-		// Now we set the rule to false to prevent SampleTask from execution.
		EngineTask<String> taskFail = new SampleTask("I have a false rule...");
		taskFail.addAndRule(new SampleRule(false));
		new EnginePipeline<String>(Arrays.asList(taskFail), new EngineData<String>()).execute();
		
+		//No output.

-		// Now back to true but with a EnginePipeline rule set to false.
		EngineTask<String> taskFailWithGeneralRule1 = new SampleTask("I have a true task rule but a false pipeline rule...");
		taskFailWithGeneralRule1.addAndRule(new SampleRule(true));
		new EnginePipeline<String>(Arrays.asList(taskFailWithGeneralRule1), null, Arrays.asList(new SampleRule(false)),
				new EngineData<String>()).execute();
		
+		//No output.

-		// Now back to true but with a EnginePipeline rule set to true.
		EngineTask<String> taskFailWithGeneralRule2 = new SampleTask("I have a true task rule and a true pipeline rule... Hello World!");
		taskFailWithGeneralRule2.addAndRule(new SampleRule(true));
		new EnginePipeline<String>(Arrays.asList(taskFailWithGeneralRule2), null, Arrays.asList(new SampleRule(true)),
				new EngineData<String>()).execute();
		
+		//output: I have a true task rule and a true pipeline rule... Hello World!
	}
}
```
# Hello World Example (with OR rules)
This example shows how an EngineTask executes with OR rules. At least one of the OR rules must pass for a task to execute. We can attach an unlimited number of OR rules to any task (You should consider performance!). We can also pass OR rules to an ExecutionPipeline. These rules will be invoked before each task in the pipeline is executed, and they will adhere to the statute of OR rules execution.

```diff
import java.util.Arrays;

import org.junit.Test;

import com.engine.EngineData;
import com.engine.EngineException;
import com.engine.EnginePipeline;
import com.engine.EngineTask;

import engine.rules.SampleRule;

public class TestHelloWorld {
	@Test
	public void test_helloWorld_or() throws EngineException {
-		// First set one rule true and one false...
		EngineTask<String> taskPass = new SampleTask("I have 2 rules true and false... Hello World!");
		taskPass.addOrRule(new SampleRule(true)).addOrRule(new SampleRule(true));
		new EnginePipeline<String>(Arrays.asList(taskPass), new EngineData<String>()).execute();
		
+		//output: I have 2 rules true and false... Hello World!

-		// Then we set both rules false
		EngineTask<String> taskFail = new SampleTask("I have 2 rules false and false... Hello World!");
		taskFail.addOrRule(new SampleRule(true)).addOrRule(new SampleRule(false));
		new EnginePipeline<String>(Arrays.asList(taskFail), new EngineData<String>()).execute();
		
+		//No output.

-		// Then we set both rules false, and we pass a pipeline true rule...
		EngineTask<String> taskFailWithGeneralRule1 = new SampleTask("I have 2 rules false and false, with a true pipeline rule... Hello World!");
		taskFailWithGeneralRule1.addAndRule(new SampleRule(true));
		new EnginePipeline<String>(Arrays.asList(taskFailWithGeneralRule1), Arrays.asList(new SampleRule(true)), null,
				new EngineData<String>()).execute();
		
+		//output: I have 2 rules false and false, with a true pipeline rule... Hello World!
	}
}
```

## License
[MIT](https://choosealicense.com/licenses/mit/)

