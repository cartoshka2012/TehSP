package com.example.mstore.controllers;


import com.example.mstore.models.User;
import com.example.mstore.repositories.UserRepository;
import com.example.mstore.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        model.addAttribute("userChannel", user);
        return "user-info";
    }
    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal User currentUser,
                          Model model) {
        //User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", currentUser);
        model.addAttribute("userChannel", currentUser);
        model.addAttribute("subscriptionsCount", currentUser.getSubscribersCount());
        model.addAttribute("subscribersCount", currentUser.getSubscriptionsCount());
        return "profile";
    }

    @GetMapping("/user/subscribe/{id}")
    public String subscribe(
            @PathVariable Long id,
            @AuthenticationPrincipal User User
    ) {

        userService.subscribe(User, id);
        userService.setSubscriptoinsCount(id, User.getId());
        System.out.println("!!!!!!!!!!!!!!!!!!!!" + User.getId());
        System.out.println("&&&&&&&&&&&&&&&" + userRepository.findUserById(id).getId());

        return "/Sps";
    }

    @GetMapping("/user/unsubscribe/{id}")
    public String unsubscribe(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser
    ) {
        userService.unsubscribe(currentUser, id);
        userService.setUnsubscriptoinsCount(id, currentUser.getId());
        return "/unsub";
    }

    @GetMapping("{type}/{user}/list")
    public String userList(
            Model model,
            @PathVariable User user,
            @PathVariable String type
    ) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);

        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }

        return "subscriptions";
    }


/*    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String displayHomePage(Model model, Principal user) {
        if (user != null) {

        }
        return "blocks/navbar";
    }*/
}