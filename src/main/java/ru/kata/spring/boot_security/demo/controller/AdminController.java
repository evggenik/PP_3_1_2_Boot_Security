package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.UserEntity;

import ru.kata.spring.boot_security.demo.service.MyUserService;
import ru.kata.spring.boot_security.demo.service.RolesService;

import java.util.List;

@Controller
//@RequestMapping("/")
public class AdminController {

    private final MyUserService myUserService;
    private final RolesService rolesService;

    @Autowired
    public AdminController(MyUserService myUserService, RolesService rolesService) {
        this.myUserService = myUserService;
        this.rolesService = rolesService;
    }


    @GetMapping("/admin")
    public String getUserName(Model model) {
        List<UserEntity> users = myUserService.allUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/admin/add")
    public String add(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("users_roles", rolesService.getAllRoles());
        System.out.println(rolesService.getAllRoles());
        return "add";
    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute("user") UserEntity user) {
        user.setRoles(user.getRoles());

        myUserService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        myUserService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        UserEntity user = myUserService.findById(id);
        List<Role> roleList = myUserService.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleList);
        return "edit";
    }
    @PostMapping("/admin/edit")
    public String updateUser(UserEntity user) {
        myUserService.saveUser(user);
        return "redirect:/admin";
    }
}
