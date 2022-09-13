<html>
	<head>
<meta name="google-site-verification" content="q9EvTtHJ8aTYDL7YHZRneNcjcRUAKdaAlgduK79zh5I" />

</head>
</html>

# Android-Stories

### Instagram like stories for Android. 

<p align="center">
<img src="https://user-images.githubusercontent.com/28200363/189760191-004367db-ce64-4f9c-81f1-29f1710d9e32.png" width="250" height="400" alt="Alt text" title="Optional title">

<img src="https://user-images.githubusercontent.com/28200363/189760642-ce48568b-438c-4844-9fb6-995cbef9f7f4.png" width="250" height="400" alt="Alt text" title="Optional title">

<img src="https://user-images.githubusercontent.com/28200363/189761287-fa193ebb-338d-4f17-97cd-856fea81f68d.png" width="250" height="400" alt="Alt text" title="Optional title">
</p>


Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency

```
dependencies {
	        implementation 'com.github.MrMak21:Android-Stories:1.1.3'
	}
```

### Usage
<p> 
  Add view to Activity/Fragment layout
</p>

```
<gr.makris.androidstories.Stories
        android:id="@+id/stories"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:progressBarBackgroundColor="@color/gray"
        app:progressBarColor="@color/white"
        app:storyDuration="3" // in Seconds
        app:loadingViewProgressColor="@color/purple_700" />
```


 Then create a **StoryItem()** lists and pass it to **Stories** view. <br>
 Kotlin example:
 
 ```
 val storiesList = listOf<StoryItem>(
            StoryItem(url = "https://picsum.photos/300/500"),
            StoryItem(src = "R.drawable.cool_image"),
            StoryItem(url = "https://picsum.photos/300/500"),
            StoryItem(src = "R.drawable.cool_image_2")
        )

        val storiesView = findViewById<Stories>(R.id.stories)
        storiesView.setStoriesList(storiesList)
 ```
 
 
 Done! You can now see the stories and interact with them just like in Instagram. <br> <br>
 Press to the right to go next, press to the left to go back or hold to stop the progress.




