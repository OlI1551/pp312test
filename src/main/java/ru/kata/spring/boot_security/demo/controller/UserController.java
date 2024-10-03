package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.showAllUsers());
        return "showAllUsers";
    }

    @GetMapping("/{id}")
    public String showUser(HttpServletRequest request, Model model) {
        String id = request.getRequestURI().split("/")[2];
        model.addAttribute("user", userService.showUserById(Long.parseLong(id)));
        return "showUserById";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "newUser";
    }

//    @PostMapping
//    public String createUser(@ModelAttribute("user") @Valid User user,
//                             BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "newUser";
//        }
//        userService.saveUser(user);
//        return "redirect:/users";
//    }
    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             @RequestParam("role") String role) {
        if (bindingResult.hasErrors()) {
            return "newUser";
        }
        userService.saveUser(user, role);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String editUser(HttpServletRequest request, Model model) {
        String id = request.getRequestURI().split("/")[2];
        model.addAttribute("user", userService.showUserById(Long.parseLong(id)));
        return "editUser";
    }

    @PostMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             HttpServletRequest request,
                             @RequestParam("role") String role) {
        if (bindingResult.hasErrors()) {
            return "editUser";
        }
        String id = request.getRequestURI().split("/")[2];
        userService.updateUser(Long.parseLong(id), user, role);
        return "redirect:/users";
    }

    @PostMapping(value = "/{id}", params = "action=del")
    public String deleteUser(HttpServletRequest request) {
        String id = request.getRequestURI().split("/")[2];
        userService.deleteUser(Long.parseLong(id));
        return "redirect:/users";
    }
}
