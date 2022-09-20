<html>
	<head>
<meta name="google-site-verification" content="q9EvTtHJ8aTYDL7YHZRneNcjcRUAKdaAlgduK79zh5I" />

</head>
</html>

# Android Stories Library

### Instagram like stories library for Android. 

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
	        implementation 'com.github.MrMak21:Android-Stories:1.1.4'
	}
```

### Usage

 - Add view to Activity/Fragment layout


```
<gr.makris.androidstories.Stories
        android:id="@+id/stories"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:progressBarBackgroundColor="@color/gray" // --> progressBar bacground color
        app:progressBarColor="@color/white" // --> progressBar color
        app:storyDuration="3" // --> in seconds
        app:loadingViewProgressColor="@color/purple_700" />
```

- You can also change the progress bar color & the background with these attributes

```
 app:progressBarBackgroundColor="@color/gray"
        app:progressBarColor="@color/white"
```



 - Then create a **StoryItem()** lists and pass it to **Stories** view. <br>
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
 
 - The ***StoryItem*** object can take 2 types of parameters: 
  1) String (Useful for url's containing images)
  2) Int (You can load a Drawable file)
 
<br>

 - Also Android Stories library view containing all the instagram-like functionality.
 Press to the ***right*** to go next story, press to the ***left*** to go to the previous story or ***hold*** to pause time.
 
 <br>
 
 - Android Stories library view also contains a callback so you know when the stories come to an end. <br>
 All you have to do is implement ***StoriesCallback*** in your Activity/Fragment and override ***onStoriesEnd()*** method. <br>
 Kotlin example: 
 
 ```
 class MainActivity : AppCompatActivity(), StoriesCallback
 	...
	...
	override fun onStoriesEnd() {
        	//do something
    }
 ```
 
 ### Upcoming things
 - [ ] Set ***marginStart*** & ***marginEnd*** of story-progress view from xml
 - [ ] Set ***margin*** between every story-progress line on top of the view
 - [ ] Support videos
 
 




