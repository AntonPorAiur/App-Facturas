package com.example.demojpa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam (value = "error", required = false) String error,
                        @RequestParam (value = "logout", required = false) String logout,
                        Model model,
                        Principal principal,
                        RedirectAttributes flash){

        if(principal != null){
            flash.addAttribute("info","Ya has iniciado sesión");
            return "redirect:/";
        }

        if(error != null){
            model.addAttribute("error","Usuario o contraseña incorrecta");
        }

        if(logout != null){
            model.addAttribute("success","Has cerrado sesión con éxito");
        }

        return "login";
    }
}
