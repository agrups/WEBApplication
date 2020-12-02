package webControllers;

import GSONSerializable.AllFMSGSONSerializer;
import GSONSerializable.AllSystemCategoriesGSONSerializer;
import GSONSerializable.CategoryGSONSerializer;
import GSONSerializable.FMSGSONSerializer;
import JDBCControllers.CategoryController;
import JDBCControllers.FMSController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Category;
import model.FinanceManagementSystem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

@Controller
public class WebCategoryController {

    CategoryController categoryControl = new CategoryController();
    Gson gson = new Gson();

    @RequestMapping(value = "category/systemCategories")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllSystemCategories(@RequestParam("systemId") int id) throws SQLException, ClassNotFoundException {

/*        List<Category> allCategories = categoryControl.getAllCategories(id);

        return this.gson.toJson(allCategories);*/

        List<Category> allCategories = categoryControl.getAllCategories(id);

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(Category.class, new CategoryGSONSerializer());
        Gson parser = gson.create();
        parser.toJson(allCategories.get(0));

        Type categoryList = new TypeToken<List<Category>>() {
        }.getType();
        gson.registerTypeAdapter(categoryList, new AllSystemCategoriesGSONSerializer());
        parser = gson.create();

        return parser.toJson(allCategories);
    }

    @RequestMapping(value = "category/categoryInfoById", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCategoryById(@RequestParam("categoryId") int id) throws SQLException, ClassNotFoundException {

        Category category = CategoryController.findCategory(id);
        return this.gson.toJson(category);
    }

    @RequestMapping(value = "category/categoryInfoByName", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCategoryByName(@RequestParam("name") String name, @RequestParam("systemId") int systemId) throws SQLException, ClassNotFoundException {

        Category category = categoryControl.findCategory(name, systemId);
        return this.gson.toJson(category);
    }

    @RequestMapping(value = "/category/updateParentCategory", method = {RequestMethod.PUT})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateParentCategory(@RequestBody String request) throws ParseException, SQLException, ClassNotFoundException {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        int categoryId = Integer.parseInt(data.getProperty("categoryId"));
        Category cat = categoryControl.findCategory(categoryId);
        int parentId = Integer.parseInt(data.getProperty("categoryParentCategoryId"));
        cat.setParentCategory(categoryControl.findCategory(parentId));

        categoryControl.updateParentCategory(cat, parentId);
        return "Updated successfully";
    }

    @RequestMapping(value = "/category/addSubcategory", method = {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addSubcategory(@RequestBody String request) throws ParseException, SQLException, ClassNotFoundException {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("categoryName");
        String description = data.getProperty("categoryDescription");
        int sysId = Integer.parseInt(data.getProperty("categorySystemId"));

        Category cat = new Category(name, description, FMSController.findSystem(sysId));
        CategoryController.createSubcategory(cat);
        return "Added successfully";
    }

    @RequestMapping(value = "/category/addResponsibleCategory", method = {RequestMethod.PUT})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addResponsibleUser(@RequestBody String request) throws ParseException, SQLException, ClassNotFoundException {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        int categoryId = Integer.parseInt(data.getProperty("categoryId"));
        int userId = Integer.parseInt(data.getProperty("responsibleUserId"));

        categoryControl.addResponUser(categoryId, userId);
        return "Updated successfully";
    }

    @RequestMapping(value = "/category/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void deleteCategory(@RequestParam int catId) throws SQLException, ClassNotFoundException {
        Category category = CategoryController.findCategory(catId);
        CategoryController.removeCategory(category);
    }

    @RequestMapping(value = "category/checkIfExist", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String checkIfExistByName(@RequestParam("name") String name) throws SQLException, ClassNotFoundException {

        boolean exist = categoryControl.categoryExist(name);
        return this.gson.toJson(exist);
    }

    @RequestMapping(value = "category/checkIfCategoriesExistInSystem", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String categoriesExistInSystem(@RequestParam("systemId") int systemId) throws SQLException, ClassNotFoundException {

        boolean exist = categoryControl.categoriesExistInSystem(systemId);
        return this.gson.toJson(exist);
    }

    @RequestMapping(value = "category/checkIfSubcategoriesExistInCategory", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String subcategoriesExistInCategory(@RequestParam("parentId") int parentId) throws SQLException, ClassNotFoundException {

        boolean exist = categoryControl.subcategoriesExistInCategory(parentId);
        return this.gson.toJson(exist);
    }

    @RequestMapping(value = "category/checkIfisResponsibleUser", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String checkIfisResponsibleUser(@RequestParam("categoryId") int categoryId, @RequestParam("userId") int userId) throws SQLException, ClassNotFoundException {

        boolean exist = categoryControl.isResponsibleUser(categoryId, userId);
        return this.gson.toJson(exist);
    }
}
