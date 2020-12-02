package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.User;

import java.lang.reflect.Type;

public class UserGSONSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject fmsJson = new JsonObject();
        fmsJson.addProperty("userid", user.getId());
        fmsJson.addProperty("userName", user.getName());
        fmsJson.addProperty("userSurname", user.getSurname());
        fmsJson.addProperty("userEmail", user.getEmail());
        fmsJson.addProperty("userPhoneNumber", user.getPhoneNumber());
        fmsJson.addProperty("isUserIndividual", user.isIndividual());
        fmsJson.addProperty("userSystemId", user.getFms().getId());
        fmsJson.addProperty("userLoginName", user.getLoginName());
        fmsJson.addProperty("userPassword", user.getPassword());
        return fmsJson;
    }
}