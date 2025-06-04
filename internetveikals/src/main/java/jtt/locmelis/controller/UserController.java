package jtt.locmelis.controller;

import jtt.locmelis.dto.OrderItems;
import jtt.locmelis.dto.Shoes;
import jtt.locmelis.dto.User;
import jtt.locmelis.dao.OrderItemsDAO;
import jtt.locmelis.dao.impl.OrderItemsDAOImpl;
import jtt.locmelis.dao.impl.ShoesDAOImpl;
import jtt.locmelis.dao.impl.UserDAOImpl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	private OrderItemsDAOImpl orderItemsDAO = new OrderItemsDAOImpl();
    private UserDAOImpl userDAO = new UserDAOImpl();
    private ShoesDAOImpl shoesDAO = new ShoesDAOImpl();

    @GetMapping("/")
    public String viewShoesCheaperThan70(Model model) throws SQLException {
        List<Shoes> shoesList = shoesDAO.getAllCheaperThan70();
        model.addAttribute("shoesList", shoesList);
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, HttpSession session, Model model) throws SQLException {
        int result = userDAO.insert(user);

        if (result == 1) {
            User loggedInUser = userDAO.getByUsername(user.getUsername());
            session.setAttribute("loggedInUser", loggedInUser);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Reģistrācija neizdevās - lietotājvārds jau aizņemts");
            return "register";
        }
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/insert")
    public String insertUser(@ModelAttribute User user) throws SQLException {
        userDAO.insert(user);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) throws SQLException {
        User user = userDAO.getById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) throws SQLException {
        userDAO.update(user);
        return "redirect:/";
    }

    @GetMapping("/delete/shoes/{id}")
    public String deleteShoe(@PathVariable int id) throws SQLException {
        shoesDAO.deleteById(id);
        return "admin"; 
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model, HttpSession session) throws SQLException {
        User loggedInUser = userDAO.getByUsername(user.getUsername());
        if (loggedInUser != null && loggedInUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("loggedInUser", loggedInUser);
            if ("admin".equals(loggedInUser.getUsername()) && "admin".equals(loggedInUser.getPassword())) {
                return "redirect:/admin";
            }
            return "redirect:/";
        } else {
            model.addAttribute("error", "Nepareizs lietotājvārds vai parole");
            return "login";
        }
    }

    @GetMapping("/admin")
    public String showAdminPage(HttpSession session, Model model) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        List<Shoes> shoesList = shoesDAO.getAll();

        if (loggedInUser != null && "admin".equals(loggedInUser.getUsername())) {
            model.addAttribute("shoesList", shoesList);
            return "admin";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/men")
    public String viewAllMenShoes(Model model) throws SQLException {
        List<Shoes> shoesList = shoesDAO.getAllByGender("Men");
        model.addAttribute("shoesList", shoesList);
        return "men";
    }

    @GetMapping("/women")
    public String viewAllWomenShoes(Model model) throws SQLException {
        List<Shoes> shoesList = shoesDAO.getAllByGender("Women");
        model.addAttribute("shoesList", shoesList);
        return "women";
    }

    @GetMapping("/kids")
    public String viewAllKidsShoes(Model model) throws SQLException {
        List<Shoes> shoesList = shoesDAO.getAllByGender("Kids");
        model.addAttribute("shoesList", shoesList);
        return "kids";
    }

    @GetMapping("/shoes/{url}")
    public String showShoeDetails(@PathVariable String url, Model model) throws SQLException {
        Shoes shoe = shoesDAO.getByUrl(url);
        if (shoe != null) {
            model.addAttribute("shoe", shoe);

            String sizesStr = shoe.getSizes();
            List<String> sizesList = List.of(sizesStr.split(","));
            model.addAttribute("sizesList", sizesList);

            return "shoe";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/izveidot")
    public String createShoes(Model model) throws SQLException {
        model.addAttribute("shoes", new Shoes());
        return "izveidot";
    }

    @GetMapping("/rediget")
    public String changeShoesInfo(Model model) throws SQLException {
        model.addAttribute("shoes", new Shoes());
        return "rediget";
    }

    @PostMapping("/shoes/create")
    public String createShoesPost(
        @RequestParam("name") String name,
        @RequestParam("brand") String brand,
        @RequestParam("price") double price,
        @RequestParam("gender") String gender,
        @RequestParam("sizes") String sizes,
        @RequestParam("color") String color,
        @RequestParam("url") String url
    ) throws SQLException {
        Shoes shoes = new Shoes();
        shoes.setName(name);
        shoes.setBrand(brand);
        shoes.setPrice(price);
        shoes.setGender(gender);
        shoes.setSizes(sizes);
        shoes.setColor(color);
        shoes.setUrl(url);

        shoesDAO.insert(shoes);

        return "redirect:/admin";
    }
    
    @PostMapping("/user/add-to-cart")
    public String addToCart(
            @RequestParam("shoeId") int shoeId,
            @RequestParam("size") String size,
            HttpSession session) throws SQLException {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        OrderItems cartItem = new OrderItems();
        cartItem.setUserId(loggedInUser.getId());
        cartItem.setShoesId(shoeId);
        cartItem.setShoesSize(size);

        orderItemsDAO.insert(cartItem);

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        List<OrderItems> orderItems = orderItemsDAO.getWithShoesByUserId(loggedInUser.getId());
        model.addAttribute("orderItems", orderItems);
        return "cart";
    }

    @GetMapping("/par/mums")
    public String viewAboutUs(Model model) throws SQLException {

        return "par_mums";
    }
}
