package wtfmachine

import javafx.scene.control.Button
import tornadofx.*

class Navigator: View() {
    override val root = borderpane {
        top = hbox {
            button("Unread") {
                action {
                    center.replaceWith(
                        find<UnreadView>().root,
                        sizeToScene = true,
                        centerOnScreen = false
                    ) // todo refresh topics
                }
            }
        }
        center<UnreadView>()
    }
}