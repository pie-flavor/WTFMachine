package wtfviewer

import javafx.scene.control.Alert
import javafx.scene.control.TextArea
import kotlinx.io.PrintWriter
import kotlinx.io.StringWriter

class ExceptionAlert(exception: Exception) : Alert(AlertType.ERROR) {
    init {
        contentText = exception.message
        val stackMessage = StringWriter()
        exception.printStackTrace(PrintWriter(stackMessage))
        val stackTraceArea = TextArea(stackMessage.toString())
        stackTraceArea.isEditable = false
        dialogPane.expandableContent = stackTraceArea
    }
}
