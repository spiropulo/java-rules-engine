# Java Rules Engine Tests

The **sample** folder runs the tests described in our main README.md. If you follow the descriptions on our home page you can run all the samples by running this the **sample** folder.

## The Tests

```diff
+ TestNoRules.java

  A simple example on how to invoke the EnginePipeline and execute a task.
```

```diff
+ TestExceptions.java

  This test describes how the engine reacts if the wrong parameters are past in.
```

```diff
+ TestAndRules.java

  A set of tests that exercise the AND rules. AND rules can be attached to any individual task, 
  multiple tasks or EnginePipeline. AND Rules that are attached to any individual task will execute 
  before those tasks, and if there is a list of them attached to any task, they must all pass 
  before the task executes. AND Rules that are a linked to EnginePipeline will execute before every
  single AND task for that instance of EnginePipeline.
```

```diff
+ TestNoRules.java

  A set of tests that exercise the OR rules. OR rules can be attached to any individual task, 
  multiple tasks or EnginePipeline. OR Rules that are attached to any individual task will execute 
  before those tasks, and if there is a list of them attached to any task, one must pass 
  before the task executes. OR Rules that are a linked to EnginePipeline will execute before every
  single OR task for that instance of EnginePipeline.
```

```diff
+ TestAndOrRules.java
  
  These tests describe how AND and OR rules behave together. We can think of 2 buckets, AND bucket
  and an OR bucket. Each bucket follows a different cadence or set of laws. When combined each 
  bucket must yield true in order for a task to execute.
```

```diff
+ TestAndEnginePipelineRules.java
  
  These tests describe EnginePipeline AND rules. EnginePipeline is the main engine component. AND
  rules past in while initialization will be part of every task executed by this instance.
```

```diff
+ TestOrEnginePipelineRules.java
  
  These tests describe EnginePipeline OR rules. EnginePipeline is the main engine component. OR
  rules past in while initialization will be part of every task executed by this instance.
```

```diff
+ TestAndOrEnginePipelineRules.java
  
  These tests describe how AND and OR rules behave when past in at initialization of a 
  an EnginePipeline. They are effectively treated just like any other AND, OR rule but they are 
  applied to every single task the instance of EnginePipeline considers executing.
```
