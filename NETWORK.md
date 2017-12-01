# Making a request toward the APIs

The App have a so call toolbox network package which allow to easily make request toward the tvbd APIs

# Entities

First we need to map the API with an Entity class. To do that please create a **Class** with the name of the API Request in the **Model** folder. This Class will need properties that you want in it. Please also define **getters and setters**. 

# Make the request toward the APIs using a Service

#### Constructor

The network package has some Manager classes which do the whole work such as catching exception, retrying the request... What you'll need to do is to create a **package** in the **api** folder with the name of the **api**
In this folder name the File **<API_NAME>Api**

This class will need a constructor with the following properties

```java
public MyApi(Context ctx) {
  this.ctx = ctx;
  // Retrieve an instance of the RequestQueueManager (singleton)
  this.queue = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
}
```

After adding this constructor we'll call the API. At this moment only **GET** and **POST** are currently supported. Please submit an issue for adding a support for other kind of request.

#### Calling the API

For Calling the API we currently have 2 Class, **GsonGetManager** and **GsonPostManager** which extends from the **Request** class (You might ask why not a GsonCommonManager, well not the same super for the class so no common class...)

For Calling the API you'll need to import your **Entity** and add the following code

```java
// GsonGetManager for get

/**
 * <ROUTE> please define a final static value in the Routes class 
 * <JSON.toString> a JSON Payload in String format
 * <Entity.class> pass the schema of the Class 
 */
@Override
public void get(HashMap<String, String> payload, final GsonCallback callback) {
  GsonPostManager<Entity> req = new GsonPostManager<>(<ROUTE>, <JSON.toString>, <Entity.class>, response -> {
    // Call the callback
    callback.onSuccess(token);
  }, error -> {
    // Handle Errors
  });

  // add the request to the request Queue
  queue.addToRequestQueue(req);
}         
```

After that you need to add the request into the Queue like this 


#### Calling callFoo from a fragment or an activity

To execute a request we need the Context. For getting this context we need to be in a Fragment or an Activity
Therefore we'll need to call our Service from the Fragment either the Activity.

For this project please add this kind of method in the **NetworkActivity**

Below is an example

```java
/**
 * <Entity> the entity used in the Service
 */
protected void testFetch() {
    UserToken fetch = new UserToken(this.getApplicationContext());

    // Pass an interface
    fetch.makeToken(new GsonCallback<Entity>() {
      @Override
      public void onSuccess(<Entity> response) {
          // Rest of the code here
      }
    });
}
```


