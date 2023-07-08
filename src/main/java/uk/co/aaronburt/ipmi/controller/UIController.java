package uk.co.aaronburt.ipmi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/")
public class UIController {

    @GetMapping("/")
    public String index() {
        return "Index";
    }

    @GetMapping("/error")
    public String catchAll() {
        return "Error";
    }

    @GetMapping("/embed/button/{button}")
    public String button(@PathVariable String button, Model model) {
        if (Objects.equals(button, "start")) {
            model.addAttribute("isStartButton", true);
        } else {
            model.addAttribute("isHaltButton", true);
        }

        return "PowerButton";
    }
}
