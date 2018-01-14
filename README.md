# Your Name

![Image of your name app](https://github.com/Tinwork/YourName/blob/master/app/src/main/res/drawable-hdpi/logo_yourname.png)

Your name is a film app that allow you to search, share, get information and save your favorite film using the TVBDI APIs

# Pre-requirements

- Android Studio latest version
- Java 1.8
- Android SDK v27
- Realm Studio / Realm Browser

# How to use

Clone or Fork this project

- Before running the project make sure that you have the **latest android studio version**
- Open the project
- Create a tvdb account, please write down your **username** and **accountID** this can be useful..

# Realm debugging process

This project use Realm as it's caching system for an offline usage. In order to help you through the debugging process we have integrate a small snippet that help you to extract the **Datas from the App**

In order to extract the data please call the method in wherever you need to. Credit goes to @Budi Oktaviyan Suryanto for his article of extracting Realm data from android app

```java
Utils.outputRealmFile();
```

As we write something on the storage we need to add permission to the app. Please add the following code to the AndroidManifeset.xml

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

Now you can compile the app !

Before launching the App make sure to **give the Storage permission to the App manually** otherwise you won't be able to write the realm file (we never use the storage thus we don't need the permission in our production App)

# Additional Realm debugging info

When the function will execute you'll see a yellow log with the following format

```shell
com.yellowman.tinwork.yourname W/STORAGE: Export path <PATH>
```
Please copy the **output path** by the console and use the following command to retrieve the Realm file

```shell
./adb pull <PATH> <Destination path of your choice>
```

# How to contribute

When creating a new feature please follow the guideline below

- Create a new branch with the following schema
 - `feature/<featureName>`
 - `issue/<issueName>`
- When finished create a PR :) and assign @MarcInthaamnouay he'll add some comment / test / merge your PR


