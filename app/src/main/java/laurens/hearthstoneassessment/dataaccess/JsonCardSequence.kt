package laurens.hearthstoneassessment.dataaccess

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import laurens.hearthstoneassessment.model.Card
import java.io.InputStream

class JsonCardSequence(private val jsonStream: InputStream) {

    fun streamCards(): Sequence<Card> {
        val parser = JsonFactory().createParser(jsonStream)
        val mapper = jacksonObjectMapper()
        return sequence {
            while (true) {
                while (parser.nextToken() != JsonToken.START_ARRAY) if (parser.currentToken == null) return@sequence
                while (parser.nextToken() != JsonToken.START_OBJECT);
                while (parser.currentToken == JsonToken.START_OBJECT) {
                    yield(mapper.readValue(parser))
                    parser.nextToken()
                }
            }
        }
    }
}