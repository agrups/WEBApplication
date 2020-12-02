package GSONSerializable;

import com.google.gson.*;
import model.User;

import java.lang.reflect.Type;
import java.util.List;

public class AllSystemUsersGSONSerializer implements JsonSerializer<List<User>> {
    @Override
    public JsonElement serialize(List<User> allSystemUsers, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, new UserGSONSerializer());
        Gson parser = gsonBuilder.create();

        for (User l : allSystemUsers) {
            jsonArray.add(parser.toJson(l));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
