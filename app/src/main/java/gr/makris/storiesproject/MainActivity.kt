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
            StoryItem(src = R.drawable.cool),
            StoryItem(src = R.drawable.mak),
//            StoryItem(src = R.drawable.cool3),
//            StoryItem(src = R.drawable.cool4),
//            StoryItem(src = R.drawable.cool1),
            StoryItem(src = R.drawable.cool5),
            StoryItem(src = R.drawable.cool)
        )

        val storiesView = findViewById<Stories>(R.id.stories)
        storiesView.setStoriesList(storiesList)
    }

    override fun onStoriesEnd() {
        println("Stories end callback")
    }

    fun load(str: String) {

    }

    fun load(img: Int) {

    }
}