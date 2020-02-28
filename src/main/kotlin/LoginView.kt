package wtfviewer

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.control.TextArea
import kotlinx.io.PrintWriter
import kotlinx.io.StringWriter
import tornadofx.*

class LoginView: View() {
    enum class LoginType(val display: String) {
        PASSWORD("Password"), SESSION("Session");

        override fun toString() = display
    }
    val username = SimpleStringProperty()
    val password = SimpleStringProperty()
    val loginType = SimpleObjectProperty<LoginType>()

    override val root = form {
        fieldset {
            field("Username") {
                label.addClass(WtfStyle.loginLabel)
                textfield(username)
            }
            field {
                textProperty.bind(loginType.asString())
                label.addClass(WtfStyle.loginLabel)
                passwordfield(password)
            }
            field("Login Mode") {
                label.addClass(WtfStyle.loginLabel)
                combobox(loginType, LoginType.values().toList()) {
                    value = LoginType.PASSWORD
                }
            }
        }
        button("Log In") {
            isDefaultButton = true
            action {
                try {
                    scope.client = when (loginType.value!!) {
                        LoginType.PASSWORD -> Client.createAndLogin(username.value, password.value)
                        LoginType.SESSION -> Client.createAndConnect(username.value, password.value)
                    }
                    replaceWith<UnreadView>(sizeToScene = true)
                } catch (e: Exception) {
                    ExceptionAlert(e).showAndWait()
                }
            }
        }
    }
    override val scope = super.scope as WtfScope
}
