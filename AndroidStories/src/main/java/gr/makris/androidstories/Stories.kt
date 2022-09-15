package gr.makris.androidstories

import android.R.attr
import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.Transition
import gr.makris.androidstories.data.StoryItem
import gr.makris.androidstories.data.getResource
import gr.makris.androidstories.listener.StoriesCallback
import java.util.*


class Stories @JvmOverloads
constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr), View.OnTouchListener{

    private lateinit var storiesList: List<StoryItem>
    private lateinit var loadingViewLayout: ConstraintLayout
    private lateinit var leftTouchPanel: FrameLayout
    private lateinit var rightTouchPanel: FrameLayout
    private lateinit var imageContentView: ImageView
    private lateinit var videoContentView: VideoView
    private lateinit var loadingView: ProgressBar

    private var progressBarBackgroundColor: Int = resources.getColor(R.color.progressGray)
    private var progressColor: Int = resources.getColor(R.color.progressWhite)
    private var loadingViewProgressColor: Int = resources.getColor(R.color.progressWhite)
    private lateinit var storyDuration: String
    private lateinit var animation: ObjectAnimator

    private var storyIndex: Int = 1
    private var userClicked: Boolean = false
    private lateinit var storiesListener: StoriesCallback
    private var oldStoryItem = StoryItem()

    init {
        applyAttributes(attrs)
        initLayout()
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(attrs, R.styleable.AndroidStories)

        progressBarBackgroundColor = attrs.getColor(R.styleable.AndroidStories_progressBarBackgroundColor, resources.getColor(R.color.progressGray))
        progressColor = attrs.getColor(R.styleable.AndroidStories_progressBarColor, resources.getColor(R.color.progressWhite))
        storyDuration = attrs.getString(R.styleable.AndroidStories_storyDuration) ?: "3"
        loadingViewProgressColor = attrs.getColor(R.styleable.AndroidStories_loadingViewProgressColor, resources.getColor(R.color.progressWhite))
        attrs.recycle()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initLayout() {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.android_stories_layout, this, false)
        addView(view)

        if (context is StoriesCallback)
            storiesListener = context as StoriesCallback


        loadingViewLayout = findViewById(R.id.progressBarContainer)
        leftTouchPanel = findViewById(R.id.leftTouchPanel)
        rightTouchPanel = findViewById(R.id.rightTouchPanel)
        imageContentView = findViewById(R.id.contentImageView)
        videoContentView = findViewById(R.id.contentVideoView)
        loadingView = findViewById(R.id.androidStoriesLoadingView)

        leftTouchPanel.setOnTouchListener(this)
        rightTouchPanel.setOnTouchListener(this)

        loadingView.indeterminateTintList = ColorStateList.valueOf((loadingViewProgressColor))
    }


    fun setStoriesList(storiesList: List<StoryItem>) {
        this.storiesList = storiesList
        addLoadingViews(storiesList)
    }

    private fun addLoadingViews(storiesList: List<StoryItem>) {
        var idcounter = 1
        for (story in storiesList) {
            val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
            progressBar.visibility = View.VISIBLE
            progressBar.id = idcounter
            progressBar.tag = "story${idcounter++}"
            progressBar.progressBackgroundTintList = ColorStateList.valueOf((progressBarBackgroundColor))
            progressBar.progressTintList = ColorStateList.valueOf((progressColor))
            val params = ConstraintLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT)
            params.marginEnd = 5
            params.marginStart = 5
            loadingViewLayout.addView(progressBar, params)
        }

        val constraintSet = ConstraintSet()
        constraintSet.clone(loadingViewLayout)

        var counter = storiesList.size
        for (story in storiesList) {
            val progressBar = findViewWithTag<ProgressBar>("story${counter}")
            if (progressBar != null) {
                if (storiesList.size > 1) {
                    if (counter == storiesList.size) {
                        constraintSet.connect(getId("story${counter}"), ConstraintSet.END, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.END)
                        constraintSet.connect(getId("story${counter}"), ConstraintSet.TOP, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.TOP)
                        constraintSet.connect(getId("story${counter}"), ConstraintSet.START, getId("story${counter-1}"), ConstraintSet.END)
                    }
                    else if (counter == 1) {
                        constraintSet.connect(getId("story${counter}"), ConstraintSet.TOP, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.TOP)
                        constraintSet.connect(getId("story${counter}"), ConstraintSet.START, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.START)
                        constraintSet.connect(getId("story${counter}"), ConstraintSet.END, getId("story${counter + 1}"), ConstraintSet.START)
                    } else {
                        constraintSet.connect(getId("story${counter}"), ConstraintSet.TOP, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.TOP)
                        constraintSet.connect(getId("story${counter}"), ConstraintSet.START, getId("story${counter-1}"), ConstraintSet.END)
                        constraintSet.connect(getId("story${counter}"), ConstraintSet.END, getId("story${counter + 1}"), ConstraintSet.START)
                    }
                } else {
                    constraintSet.connect(getId("story${counter}"), ConstraintSet.END, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.END)
                    constraintSet.connect(getId("story${counter}"), ConstraintSet.TOP, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.TOP)
                    constraintSet.connect(getId("story${counter}"), ConstraintSet.START, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.START)
                }
            }
            counter--
        }
        constraintSet.applyTo(loadingViewLayout)
        startShowContent()
    }

    private fun startShowContent() {
        showStory()
    }

    private fun showStory() {
        val progressBar = findViewWithTag<ProgressBar>("story${storyIndex}")
        loadingView.visibility = View.VISIBLE
        loadStory(storiesList[storyIndex - 1])

        animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        animation.duration = secondsToMillis(storyDuration)
        animation.interpolator = LinearInterpolator()
        animation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
            }
            override fun onAnimationEnd(animator: Animator) {
                if (storyIndex - 1 <= storiesList.size) {
                    if (userClicked) {
                        userClicked = false
                    } else {
                        if (storyIndex < storiesList.size) {
                            storyIndex += 1
                            showStory()
                        } else {
                            // on stories end
                            loadingView.visibility = View.GONE
                            onStoriesCompleted()
                        }
                    }
                } else {
                    // on stories end
                    loadingView.visibility = View.GONE
                    onStoriesCompleted()
                }
            }

            override fun onAnimationCancel(animator: Animator) {
                progressBar.progress = 100
            }
            override fun onAnimationRepeat(animator: Animator) {}
        })
    }

    private fun getId(tag: String): Int {
        return findViewWithTag<ProgressBar>(tag).id
    }

    private fun secondsToMillis(seconds: String): Long {
        return (seconds.toLongOrNull() ?: 3).times(1000)
    }

    private fun resetProgressBar(storyIndex: Int) {
        val currentProgressBar = findViewWithTag<ProgressBar>("story${storyIndex}")
        val lastProgressBar = findViewWithTag<ProgressBar>("story${storyIndex - 1}")
        currentProgressBar?.let {
            it.progress = 0
        }
        lastProgressBar?.let {
            it.progress = 0
        }
    }

    private fun completeProgressBar(storyIndex: Int) {
        val lastProgressBar = findViewWithTag<ProgressBar>("story${storyIndex}")
        lastProgressBar?.let {
            it.progress = 100
        }
    }



    var startClickTime = 0L
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        val MAX_CLICK_DURATION = 200
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startClickTime = Calendar.getInstance().timeInMillis
                animation.pause()
            }
            MotionEvent.ACTION_UP -> {
                val clickDuration = Calendar.getInstance().timeInMillis - startClickTime
                if (clickDuration < MAX_CLICK_DURATION) {
                    //click occurred
                    view?.let {
                        if (it.id == R.id.leftTouchPanel) {
                            leftPanelTouch()
                        } else if (it.id == R.id.rightTouchPanel) {
                            rightPanelTouch()
                        }
                    }
                } else {
                    //hold click occurred
                    animation.resume()
                }
            }
        }
        return true
    }

    private fun rightPanelTouch() {
        if (storyIndex == storiesList.size) {
            completeProgressBar(storyIndex)
            onStoriesCompleted()
            return
        }
        userClicked = true
        animation.end()
        if (storyIndex < storiesList.size)
            storyIndex += 1
        showStory()
    }

    private fun leftPanelTouch() {
        userClicked = true
        animation.end()
        resetProgressBar(storyIndex)
        if (storyIndex > 1)
            storyIndex -= 1
        showStory()
    }

    private fun onStoriesCompleted() {
        if (::storiesListener.isInitialized)
            storiesListener.onStoriesEnd()
    }

    private fun loadStory(story: StoryItem) {
        Glide.with(context)
            .load(story.getResource())
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .centerCrop()
            .dontAnimate()
            .placeholder(imageContentView.drawable)
            .thumbnail(Glide
                .with(context)
                .load(oldStoryItem.getResource())
                .centerCrop())
            .listener(
                object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadingView.visibility = View.GONE
                        animation.start()
                        return false
                    }

                }
            )
            .into(imageContentView)


        oldStoryItem = story
    }


}