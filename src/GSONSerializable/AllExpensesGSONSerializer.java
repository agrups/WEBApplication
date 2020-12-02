package GSONSerializable;

import com.google.gson.*;
import model.Expenses;
import model.Income;

import java.lang.reflect.Type;
import java.util.List;

public class AllExpensesGSONSerializer implements JsonSerializer<List<Expenses>> {
    @Override
    public JsonElement serialize(List<Expenses> allIncome, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Income.class, new ExpensesGSONSerializer());
        Gson parser = gsonBuilder.create();

        for (Expenses l : allIncome) {
            jsonArray.add(parser.toJson(l));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
