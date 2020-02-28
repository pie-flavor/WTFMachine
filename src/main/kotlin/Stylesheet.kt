package wtfmachine

import tornadofx.*

class WtfStyle: Stylesheet() {
    companion object {
        val topicListTopicTitle by cssclass()
        val topicListDesc by cssclass()
        val pageTitle by cssclass()
        val loginLabel by cssclass()
    }

    init {
        topicListTopicTitle {
            fontSize = 18.px
            fontFamily = "Helvetica"
        }
        topicListDesc {
            fontSize = 11.px
            fontFamily = "Helvetica"
        }
        pageTitle {
            fontSize = 25.px
            fontFamily = "Helvetica"
        }
        loginLabel {
            fontSize = 15.px
            fontFamily = "Helvetica"
        }
        root {
            padding = box(20.px)
        }
    }
}
