package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Expenses;

import java.lang.reflect.Type;

public class ExpensesGSONSerializer implements JsonSerializer<Expenses> {
    @Override
    public JsonElement serialize(Expenses expenses, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject fmsJson = new JsonObject();
        fmsJson.addProperty("expensesId", expenses.getId());
        fmsJson.addProperty("expensesName", expenses.getName());
        fmsJson.addProperty("expensesAmount", expenses.getPrice());
        fmsJson.addProperty("expensesDate", expenses.getDateCreated().toString());
        fmsJson.addProperty("categoryId", expenses.getCategory().getId());
        return fmsJson;
    }
}