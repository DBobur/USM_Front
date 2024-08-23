package uz.pro.usm_front.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pro.usm_front.domain.dto.request.user.LoginRequest;
import uz.pro.usm_front.domain.dto.request.user.RegisterRequest;
import uz.pro.usm_front.domain.dto.response.user.RoleResponse;
import uz.pro.usm_front.domain.dto.response.user.UserResponse;
import uz.pro.usm_front.service.AuthService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";  // "login.html" sahifasini qaytaradi
    }

    @PostMapping("/login")
    public String token(@RequestBody LoginRequest request, HttpSession session) {
        String jwtToken = authService.login(request);
        session.setAttribute("jwtToken", jwtToken); // Tokenni sessiyada saqlash
        return "redirect:/home"; // Bosh sahifaga o'tish
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session) {
        String jwtToken = (String) session.getAttribute("jwtToken");

        if (jwtToken == null) {
            return "redirect:/auth/login"; // Agar token yo'q bo'lsa, login sahifasiga o'tish
        }

        // Rollarni olish va modelga qo'shish
        List<RoleResponse> roles = authService.getAllRoles(jwtToken);
        model.addAttribute("roles", roles);
        model.addAttribute("registerRequest", new RegisterRequest());

        return "auth/register";  // "register.html" sahifasini qaytaradi
    }


    @PostMapping("/register")
    public String register(@ModelAttribute("registerRequest") RegisterRequest registerRequest, Model model) {
        UserResponse userResponse = authService.register(registerRequest);
        model.addAttribute("user", userResponse);
        return "redirect:/login";  // Muvaffaqiyatli ro'yxatdan o'tgandan keyin login sahifasiga yo'naltirish
    }
}
