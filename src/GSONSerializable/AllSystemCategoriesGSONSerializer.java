package GSONSerializable;

import com.google.gson.*;
import model.Category;

import java.lang.reflect.Type;
import java.util.List;

public class AllSystemCategoriesGSONSerializer implements JsonSerializer<List<Category>> {
    @Override
    public JsonElement serialize(List<Category> allCategories, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Category.class, new CategoryGSONSerializer());
        Gson parser = gsonBuilder.create();

        for (Category l : allCategories) {
            jsonArray.add(parser.toJson(l));
        }
        System.out.println(jsonArray);
        return jsonArray;
    }
}
