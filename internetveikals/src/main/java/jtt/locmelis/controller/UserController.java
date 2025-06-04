package jtt.locmelis.controller;

import jtt.locmelis.dto.Shoes;
import jtt.locmelis.dto.User;
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

	private UserDAOImpl userDAO = new UserDAOImpl();
	private ShoesDAOImpl shoesDAO = new ShoesDAOImpl();

    @GetMapping("/")
    public String viewShoesCheaperThan70(Model model) throws SQLException {
        List<Shoes> shoesList = shoesDAO.getAllCheaperThan70();
        model.addAttribute("shoesList", shoesList);
        return "index";
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

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) throws SQLException {
        userDAO.deleteById(id);
        return "redirect:/";
    }
    
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model, HttpSession session) throws SQLException {
        User existingUser = userDAO.getByCredentials(user.getUsername(), user.getPassword());
        
        if (existingUser != null) {
            session.setAttribute("loggedInUser", existingUser);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Nepareizs lietotājvārds vai parole");
            return "login";
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
            return "shoe";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/shoe/{id}")
    public String showShoe(@PathVariable int id, Model model) {
        try {
            Shoes shoe = shoesDAO.getById(id);
            String sizes = shoe.getSizes();
            
            List<String> sizeList = shoesDAO.getSizesAsList(sizes);
            model.addAttribute("shoe", shoe);
            model.addAttribute("sizeList", sizeList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "shoe";
    }

}

