package uz.pro.usm_front.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pro.usm_front.domain.dto.request.user.UserUpdateRequest;
import uz.pro.usm_front.domain.dto.response.user.UserResponse;
import uz.pro.usm_front.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getAllUsers(Model model, HttpSession session) {
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken == null) {
            return "redirect:/auth/login";
        }

        List<UserResponse> users = userService.getAllUsers(jwtToken);
        model.addAttribute("users", users);
        return "user/user-list";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model, HttpSession session) {
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken == null) {
            return "redirect:/auth/login";
        }

        UserResponse user = userService.getUserById(id, jwtToken);
        model.addAttribute("user", user);
        return "user/user-detail";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute UserUpdateRequest userUpdateRequest,
                             @PathVariable Long id,
                             HttpSession session) {
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken == null) {
            return "redirect:/auth/login";
        }

        userService.updateUser(id, userUpdateRequest, jwtToken);
        return "redirect:/user";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id, HttpSession session) {
        String jwtToken = (String) session.getAttribute("jwtToken");
        if (jwtToken == null) {
            return "redirect:/auth/login";
        }

        userService.deleteUser(id, jwtToken);
        return "redirect:/user";
    }
}
