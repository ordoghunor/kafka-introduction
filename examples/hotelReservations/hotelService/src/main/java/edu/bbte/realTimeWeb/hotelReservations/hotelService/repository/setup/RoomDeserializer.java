package edu.bbte.realTimeWeb.hotelReservations.hotelService.repository.setup;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import edu.bbte.realTimeWeb.hotelReservations.hotelService.model.Room;

import java.io.IOException;
import java.util.ArrayList;

public class RoomDeserializer extends JsonDeserializer<Room> {
    @Override
    public Room deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int roomNumber = node.get("roomNumber").asInt();
        int type = node.get("type").asInt();
        int price = node.get("price").asInt();
        boolean isAvailable = node.get("isAvailable").asBoolean(); // Explicitly parse boolean value
        return new Room(roomNumber, type, price, isAvailable, new ArrayList<>());
    }
}
