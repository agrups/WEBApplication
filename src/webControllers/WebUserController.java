package webControllers;


import GSONSerializable.AllFMSGSONSerializer;
import GSONSerializable.AllSystemUsersGSONSerializer;
import GSONSerializable.UserGSONSerializer;
import JDBCControllers.FMSController;
import JDBCControllers.UserController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Controller
public class WebUserController {

    UserController userControl = new UserController();
    Gson gson = new Gson();

    @RequestMapping(value = "user/systemUsers", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getSystemUsers(@RequestParam("systemId") int id) throws SQLException, ClassNotFoundException {

        if (id == 0) return "No system id provided";

        List<User> allSystemUsers = userControl.getAllSysUsers(id);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserGSONSerializer());
        Gson parser = gson.create();
        parser.toJson(allSystemUsers.get(0));

        Type userList = new TypeToken<List<User>>() {
        }.getType();
        gson.registerTypeAdapter(userList, new AllFMSGSONSerializer());
        parser = gson.create();

        return parser.toJson(allSystemUsers);
    }

    @RequestMapping(value = "user/categoryUsers", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCategoryUsers(@RequestParam("categoryId") int id) throws SQLException, ClassNotFoundException {

        if (id == 0) return "No system id provided";

        List<User> allCategoryUsers = userControl.getUsers(id);

        if (allCategoryUsers.size() == 0) {
            return null;
        } else {
            GsonBuilder gson = new GsonBuilder();
            gson.registerTypeAdapter(User.class, new UserGSONSerializer());
            Gson parser = gson.create();
            parser.toJson(allCategoryUsers.get(0));

            Type userList = new TypeToken<List<User>>() {
            }.getType();
            gson.registerTypeAdapter(userList, new AllSystemUsersGSONSerializer());
            parser = gson.create();

            return parser.toJson(allCategoryUsers);
        }
    }

    @RequestMapping(value = "user/userInfoByName", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUserInfoByName(@RequestParam("name") String name, @RequestParam("systemId") int id) throws SQLException, ClassNotFoundException {

        if (name.equals("") || id == 0) return "No user name provided";

        User user = userControl.findUser(name, id);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserGSONSerializer());
        Gson parser = gson.create();
        return parser.toJson(user);
    }

    @RequestMapping(value = "user/userInfoById", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUserInfoById(@RequestParam("id") int id) throws SQLException, ClassNotFoundException {

        if (id == 0) return "No user id provided";

        User user = userControl.findUser(id);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserGSONSerializer());
        Gson parser = gson.create();
        return parser.toJson(user);
    }

    @RequestMapping(value = "user/userInfo", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUserInfoByLoginNameAndPassword(@RequestParam("loginName") String loginName, @RequestParam("password") String password) throws SQLException, ClassNotFoundException {

        if (loginName.equals("") || password.equals("")) return "No user login name and password provided";

        User user = userControl.findUser(loginName, password);
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserGSONSerializer());
        Gson parser = gson.create();
        return parser.toJson(user);
    }


    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String login(@RequestBody String request) throws SQLException, ClassNotFoundException {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String loginName = data.getProperty("login");
        String password = data.getProperty("psw");
        User user = userControl.findUser(loginName, password);

        if (user == null) {
            return "Wrong credentials";
        }
        return Integer.toString(user.getId());
    }


    @RequestMapping(value = "/user/add", method = {RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addUser(@RequestBody String request) throws SQLException, ClassNotFoundException {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        String loginName = data.getProperty("loginName");
        String password = data.getProperty("password");
        String name = data.getProperty("name");
        String surname = data.getProperty("surname");
        String email = data.getProperty("email");
        String phoneNumber = data.getProperty("phoneNumber");
        boolean individual = Boolean.valueOf(data.getProperty("par"));
        int systemId = Integer.parseInt(data.getProperty("systemId"));

        User user = new User(name, surname, email, phoneNumber, individual, loginName, password, FMSController.findSystem(systemId));
        UserController.create(user);
        return "Added successfully";
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public void deleteUser(@RequestParam int userId) throws SQLException, ClassNotFoundException {
        User user = userControl.findUser(userId);
        userControl.remove(user);
    }


    @RequestMapping(value = "user/checkIfUserExists", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String checkIfisuserExists(@RequestParam("loginName") String login) throws SQLException, ClassNotFoundException {

        boolean exist = userControl.userExists(login);
        return this.gson.toJson(exist);
    }
}
