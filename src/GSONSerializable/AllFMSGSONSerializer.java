package GSONSerializable;

import com.google.gson.*;
import model.FinanceManagementSystem;

import java.lang.reflect.Type;
import java.util.List;

public class AllFMSGSONSerializer implements JsonSerializer<List<FinanceManagementSystem>> {
    @Override
    public JsonElement serialize(List<FinanceManagementSystem> allFMS, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(FinanceManagementSystem.class, new FMSGSONSerializer());
        Gson parser = gsonBuilder.create();

        for (FinanceManagementSystem l : allFMS) {
            jsonArray.add(parser.toJson(l));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
