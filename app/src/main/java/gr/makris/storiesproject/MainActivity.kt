package gr.makris.storiesproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gr.makris.androidstories.Stories
import gr.makris.androidstories.data.StoryItem
import gr.makris.androidstories.listener.StoriesCallback

class MainActivity : AppCompatActivity(), StoriesCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val storiesList = listOf<StoryItem>(
//            StoryItem(url = "https://image.tmdb.org/t/p/w92/dRLSoufWtc16F5fliK4ECIVs56p.jpg"),
//            StoryItem(url = "https://picsum.photos/300/500"),
//            StoryItem(url = "https://picsum.photos/300/500"),
//            StoryItem(src = R.drawable.cool),
//            StoryItem(src = R.drawable.cool),
//            StoryItem(url = "https://picsum.photos/300/500"),
//            StoryItem(url = "https://picsum.photos/300/500")
//        )

        val storiesList = listOf<StoryItem>(
            StoryItem(url = "https://fakeimg.pl/100x400/"),
            StoryItem(url = "https://fakeimg.pl/200x400/"),
            StoryItem(url = "https://fakeimg.pl/300x400/"),
            StoryItem(url = "https://fakeimg.pl/400x400/")
        )

        val storiesView = findViewById<Stories>(R.id.stories)
        storiesView.setStoriesList(storiesList)
    }

    override fun onStoriesEnd() {
        println("Stories end callback")
    }
}