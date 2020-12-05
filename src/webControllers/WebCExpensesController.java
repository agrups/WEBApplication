package webControllers;

import GSONSerializable.AllExpensesGSONSerializer;
import GSONSerializable.ExpensesGSONSerializer;
import JDBCControllers.CategoryController;
import JDBCControllers.IncomeExpensesController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Expenses;
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
public class WebCExpensesController {

    IncomeExpensesController incomeExpensesController = new IncomeExpensesController();
    CategoryController categoryControl = new CategoryController();

    @RequestMapping(value = "expenses/expensesList")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCategoryExpenses(@RequestParam("categoryId") int id) throws SQLException, ClassNotFoundException {

        List<Expenses> allIncome = incomeExpensesController.getExpenses(id);
        if(allIncome.size() == 0){
            return null;
        }else {
            GsonBuilder gson = new GsonBuilder();
            gson.registerTypeAdapter(Expenses.class, new ExpensesGSONSerializer());
            Gson parser = gson.create();
            parser.toJson(allIncome.get(0));

            Type libraryList = new TypeToken<List<Expenses>>() {
            }.getType();
            gson.registerTypeAdapter(libraryList, new AllExpensesGSONSerializer());
            parser = gson.create();

            return parser.toJson(allIncome);
        }
    }

    @RequestMapping(value = "/expenses/add", method = {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addCIncome(@RequestBody String request) throws ParseException, SQLException, ClassNotFoundException {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String name = data.getProperty("expensesName");
        int amount = Integer.parseInt(data.getProperty("expensesAmount"));
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data.getProperty("expensesDate"));
        int catId = Integer.parseInt(data.getProperty("categoryId"));
        incomeExpensesController.createExpenses(new Expenses(name, amount, date, categoryControl.findCategory(catId)));
        return "Added successfully";
    }

}
