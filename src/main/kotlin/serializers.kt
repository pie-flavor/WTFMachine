package wtfviewer

import javafx.scene.paint.Color
import kotlinx.serialization.*
import kotlinx.serialization.internal.IntSerializer
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.internal.nullable
import tornadofx.*
import java.time.Instant
import kotlin.math.roundToInt

@Serializer(forClass = Instant::class)
object InstantSerializer: KSerializer<Instant> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("ISO")

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.parse(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, obj: Instant) {
        encoder.encodeString(obj.toString())
    }
}

@Serializer(forClass = Boolean::class)
object IntBoolSerializer: KSerializer<Boolean> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("Int Bool")

    override fun deserialize(decoder: Decoder): Boolean {
        return when (val value = decoder.decodeString()) {
            "true" -> true
            "false" -> false
            else -> value.toInt() != 0
        }
    }
}

@Serializer(forClass = Color::class)
object ColorSerializer: KSerializer<Color> {
    override val descriptor: SerialDescriptor = StringDescriptor.withName("JavaFX_Color")

    override fun deserialize(decoder: Decoder): Color {
        return Color.valueOf(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, obj: Color) {
        encoder.encodeString(String.format("#%02X%02X%02x", obj.red.roundToInt() * 255, obj.green.roundToInt() * 255,
            obj.blue.roundToInt() * 255))
    }
}
