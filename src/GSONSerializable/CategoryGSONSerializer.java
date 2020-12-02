package GSONSerializable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Category;

import java.lang.reflect.Type;

public class CategoryGSONSerializer implements JsonSerializer<Category> {
    @Override
    public JsonElement serialize(Category category, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject fmsJson = new JsonObject();
        fmsJson.addProperty("categoryId", category.getId());
        fmsJson.addProperty("categoryName", category.getName());
        fmsJson.addProperty("categoryDescription", category.getDescription());
/*        fmsJson.addProperty("categorySystemId", category.getFms().getId());
        fmsJson.addProperty("categoryParentCategoryId", category.getParentCategory().getId());*/
        return fmsJson;
    }
}
