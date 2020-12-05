package webControllers;

import GSONSerializable.AllIncomeGSONSerializer;
import GSONSerializable.IncomeGSONSerializer;
import JDBCControllers.CategoryController;
import JDBCControllers.IncomeExpensesController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Income;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Controller
public class WebIncomeController {

    IncomeExpensesController incomeExpensesController = new IncomeExpensesController();
    CategoryController categoryControl = new CategoryController();

    @RequestMapping(value = "income/incomeList")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCategoryIncome(@RequestParam("categoryId") int id) throws SQLException, ClassNotFoundException {
        List<Income> allIncome = incomeExpensesController.getIncome(id);
        if(allIncome.size() == 0){
            return null;
        }else{
            GsonBuilder gson = new GsonBuilder();
            gson.registerTypeAdapter(Income.class, new IncomeGSONSerializer());
            Gson parser = gson.create();
            parser.toJson(allIncome.get(0));

            Type libraryList = new TypeToken<List<Income>>() {
            }.getType();
            gson.registerTypeAdapter(libraryList, new AllIncomeGSONSerializer());
            parser = gson.create();

            return parser.toJson(allIncome);
        }
    }

    @RequestMapping(value = "/income/add", method = {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addCIncome(@RequestBody String request) throws ParseException, SQLException, ClassNotFoundException {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("incomeName");
        int amount = Integer.parseInt(data.getProperty("incomeAmount"));
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data.getProperty("incomeDate"));
        int catId = Integer.parseInt(data.getProperty("categoryId"));
        incomeExpensesController.create(new Income(name, amount, date, categoryControl.findCategory(catId)));
        return "Added successfully";
    }

}
