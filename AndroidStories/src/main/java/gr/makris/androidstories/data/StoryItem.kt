package gr.makris.androidstories.data

data class StoryItem(
    val url: String = "",
    val src: Int = -1
) {
}


fun StoryItem.getResource(): Any {
    return if (this.url.isNotEmpty()) {
         url
    } else {
        return if (this.src != -1) {
             this.src
        } else {
            ""
        }
    }
}
