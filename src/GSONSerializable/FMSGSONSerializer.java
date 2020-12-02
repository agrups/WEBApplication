package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.FinanceManagementSystem;

import java.lang.reflect.Type;

public class FMSGSONSerializer implements JsonSerializer<FinanceManagementSystem> {
    @Override
    public JsonElement serialize(FinanceManagementSystem fms, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject fmsJson = new JsonObject();
        fmsJson.addProperty("fmsId", fms.getId());
        fmsJson.addProperty("fmsName", fms.getName());
        fmsJson.addProperty("fmsDate", fms.getDateCreated().toString());
        fmsJson.addProperty("fmsVersion", fms.getSystemVersion());
        return fmsJson;
    }
}
