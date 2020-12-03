package webControllers;

import GSONSerializable.AllFMSGSONSerializer;
import GSONSerializable.FMSGSONSerializer;
import JDBCControllers.CategoryController;
import JDBCControllers.FMSController;
import JDBCControllers.IncomeExpensesController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import controllers.MainSystemWindow;
import model.Category;
import model.Expenses;
import model.FinanceManagementSystem;
import model.Income;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

@Controller
public class WebFMSController {

    Gson gson = new Gson();

    FMSController systemControl = new FMSController();

    @RequestMapping(value = "fms/fmsList")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllFMS() throws SQLException, ClassNotFoundException {

        List<FinanceManagementSystem> allFMS = systemControl.getAllFMS();

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(FinanceManagementSystem.class, new FMSGSONSerializer());
        Gson parser = gson.create();
        parser.toJson(allFMS.get(0));

        Type libraryList = new TypeToken<List<FinanceManagementSystem>>() {
        }.getType();
        gson.registerTypeAdapter(libraryList, new AllFMSGSONSerializer());
        parser = gson.create();

        return parser.toJson(allFMS);
    }

    @RequestMapping(value = "fms/fmsInfo", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getFMSInfoById(@RequestParam("systemId") int id) throws SQLException, ClassNotFoundException {
        // create Token for validation Token token = new Token();
        // token check String libName = name; ...
        if (id == 0) return "No system id provided";

        FinanceManagementSystem fms = systemControl.findSystem(id);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(FinanceManagementSystem.class, new FMSGSONSerializer());
        Gson parser = gson.create();
        return parser.toJson(fms);
    }

    @RequestMapping(value = "fms/balance", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getBalance(@RequestParam("systemId") int id) throws SQLException, ClassNotFoundException {
        // create Token for validation Token token = new Token();
        // token check String libName = name; ...
        if (id == 0) return "No system id provided";
        int allIncome = 0;
        for (Category cat : CategoryController.getAllCategories(id)) {
            for (Income inc : IncomeExpensesController.getIncome(cat.getId())) {
                allIncome += inc.getPrice();
            }
        }

        int allExpenses = 0;
        for (Category cat : CategoryController.getAllCategories(id)) {
            for (Expenses expenses : IncomeExpensesController.getExpenses(cat.getId())) {
                System.out.println(expenses.getPrice());
                allExpenses += expenses.getPrice();
            }
        }
        int balance = allIncome - allExpenses;
        GsonBuilder gson = new GsonBuilder();
        Gson parser = gson.create();
        return parser.toJson(balance);
    }



/*    @RequestMapping(value = "fms/getFinanceSystem", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getFMSInfoById(@RequestParam("companyName") String companyName, @RequestParam("id") int id) throws SQLException, ClassNotFoundException {

        if (id == 0) return "No system id provided";

        FinanceManagementSystem fms = systemControl.getFinanceSystem(companyName, id);
        return this.gson.toJson(fms);
    }*/
}
