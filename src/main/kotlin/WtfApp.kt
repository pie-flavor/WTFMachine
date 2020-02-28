package wtfmachine

import javafx.scene.image.Image
import javafx.stage.Stage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import tornadofx.*
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class WtfApp : App(
    LoginView::class,
    WtfStyle::class
) {
    init {
        reloadStylesheetsOnFocus()
    }

    override fun start(stage: Stage) {
        super.start(stage)
        addStageIcon(Image(WtfApp::class.java.classLoader.getResource("wtf.png")!!.toExternalForm()), scope)
    }

    override var scope: Scope = WtfScope()
}

val json = Json(JsonConfiguration.Stable.copy(strictMode = false, encodeDefaults = false))

val timeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    .withZone(ZoneId.systemDefault())
    .withLocale(Locale.getDefault())!!

class WtfScope : Scope() {
    lateinit var client: Client
}
