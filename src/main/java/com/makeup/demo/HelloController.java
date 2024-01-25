package com.makeup.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("client2")
@Controller
@RequiredArgsConstructor
public class HelloController {

    private final ClientService clientService;



    @GetMapping
    public String showOrderForm(Model model) {

            model.addAttribute("clientDto", new ClientDto());
            return "save";
        }
    @PostMapping
    public String save(@ModelAttribute("clientDto")  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final ClientDto clientDto) {
        clientService.save(clientDto);
        return "save";
    }

}
