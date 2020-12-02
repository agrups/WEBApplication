package GSONSerializable;

import com.google.gson.*;
import model.Income;

import java.lang.reflect.Type;
import java.util.List;

public class AllIncomeGSONSerializer implements JsonSerializer<List<Income>> {
    @Override
    public JsonElement serialize(List<Income> allIncome, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Income.class, new IncomeGSONSerializer());
        Gson parser = gsonBuilder.create();

        for (Income l : allIncome) {
            jsonArray.add(parser.toJson(l));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
