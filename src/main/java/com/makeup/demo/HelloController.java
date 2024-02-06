package com.makeup.demo;

import com.makeup.demo.exception.EntityException;
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



    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("clientDto", new ClientDto());
        return "hello";
    }

    @GetMapping
    public String showOrderForm(Model model) {

            model.addAttribute("clientDto", new ClientDto());
            return "save";
        }
    @PostMapping
    public String save(@ModelAttribute("clientDto")  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final ClientDto clientDto, Model model) {
        try {
            clientService.save(clientDto);
            model.addAttribute("info", "Pomyślnie zapisano !!!");
            return "hello";
        } catch (EntityException e){
            model.addAttribute("error", "Termin nie jest dostępny");
            return "hello";
        }







    }

}
