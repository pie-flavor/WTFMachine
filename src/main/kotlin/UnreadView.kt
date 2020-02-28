package wtfmachine

import javafx.geometry.Orientation
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import tornadofx.*

class UnreadView : View("The WTF Machine") {
    val controller: UnreadController by inject()

    override val root = vbox {
        prefHeight = 600.0
        borderpane {
            left = label("Unread")
            right = label("🔁")
            addClass(WtfStyle.pageTitle)
        }
        separator(orientation = Orientation.HORIZONTAL)
        scrollpane {
            isFitToHeight = true
            isFitToWidth = true
            vbox {
                bindComponents(controller.topics) {
                    find<TopicLine>("topic" to it, "client" to controller.client)
                }
            }
        }
    }
}

class UnreadController : Controller() {
    override val scope = super.scope as WtfScope
    val client: Client = scope.client
    val topics = observableListOf(client.getUnreadTopics().topics)
}

class TopicLine : Fragment() {
    val topic: Topic by param()
    val client: Client by param()

    override val root = if (topic.cid == 16) {
        pane()
    } else {
        vbox {
            text(topic.title) {
                addClass(WtfStyle.topicListTopicTitle)
            }
            textflow {
                text(topic.category(client).name) {
                    style {
                        fill = topic.category(client).bgColor
                    }
                }
                text(" - ${timeFormatter.format(topic.timestamp)} - ${topic.user(client).username}")
                addClass(WtfStyle.topicListDesc)
            }
        }
    }
}
