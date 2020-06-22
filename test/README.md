# Java Rules Engine Tests

The **sample** folder runs the tests described in our main README.md. If you follow the descriptions in our home page you can run all the samples by running: **sample** folder.

## The Tests

```diff
+ TestNoConditions.java

  A simple example on how to invoke the EnginePipeline and execute a action.
```

```diff
+ TestExceptions.java

  This test describes how the engine reacts if the wrong parameters are past in.
```

```diff
+ TestAndConditions.java

  A set of tests that exercise the AND conditions. AND conditions can be attached to any individual action, 
  multiple actions or EnginePipeline. AND Conditions that are attached to any individual action will execute 
  before those actions, and if there is a list of them attached to any action, they must all pass 
  before the action executes. AND Conditions that are a linked to EnginePipeline will execute before every
  single AND action for that instance of EnginePipeline.
```

```diff
+ TestNoConditions.java

  A set of tests that exercise the OR conditions. OR conditions can be attached to any individual action, 
  multiple actions or EnginePipeline. OR Conditions that are attached to any individual action will execute 
  before those actions, and if there is a list of them attached to any action, one must pass 
  before the action executes. OR Conditions that are a linked to EnginePipeline will execute before every
  single OR action for that instance of EnginePipeline.
```

```diff
+ TestAndOrConditions.java
  
  These tests describe how AND and OR conditions behave together. We can think of 2 buckets, AND bucket
  and an OR bucket. Each bucket follows a different cadence or set of laws. When combined each 
  bucket must yield true in order for a action to execute.
```

```diff
+ TestAndEnginePipelineConditions.java
  
  These tests describe EnginePipeline AND conditions. EnginePipeline is the main engine component. AND
  conditions past in while initialization will be part of every action executed by this instance.
```

```diff
+ TestOrEnginePipelineConditions.java
  
  These tests describe EnginePipeline OR conditions. EnginePipeline is the main engine component. OR
  conditions past in while initialization will be part of every action executed by this instance.
```

```diff
+ TestAndOrEnginePipelineConditions.java
  
  These tests describe how AND and OR conditions behave when past in at initialization of a 
  an EnginePipeline. They are effectively treated just like any other AND, OR condition but they are 
  applied to every single action the instance of EnginePipeline considers executing.
```
