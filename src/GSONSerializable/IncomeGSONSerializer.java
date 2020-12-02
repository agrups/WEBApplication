package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Income;

import java.lang.reflect.Type;

public class IncomeGSONSerializer implements JsonSerializer<Income> {
    @Override
    public JsonElement serialize(Income income, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject fmsJson = new JsonObject();
        fmsJson.addProperty("incomeId", income.getId());
        fmsJson.addProperty("incomeName", income.getName());
        fmsJson.addProperty("incomeAmount", income.getPrice());
        fmsJson.addProperty("incomeDate", income.getDateCreated().toString());
        fmsJson.addProperty("categoryId", income.getCategory().getId());
        return fmsJson;
    }
}