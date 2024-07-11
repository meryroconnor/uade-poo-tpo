package LocalDateTimeAdapter;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

//Esto sirve para que el escritor de DAOs (Gson de Google) pueda reconocer
// y tipar lo que es el manejo de fechas de la biblioteca LocalDateTime

public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT); //Fecha del dia larga y horario corto (sin segundos)

    @Override
    public JsonElement serialize(LocalDateTime LocalDateTime,
                                 Type type,
                                 JsonSerializationContext jsonSerializationContext) {

        return new JsonPrimitive(LocalDateTime.format(formatter)); // "yyyy-MM-dd hh-mm-ss"

    }

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement,
                                     Type type,
                                     JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        return LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), formatter);
    }
}
