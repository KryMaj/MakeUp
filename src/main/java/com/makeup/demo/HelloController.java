package com.makeup.demo;

import com.makeup.demo.exception.EntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("client2")
@Controller
@RequiredArgsConstructor
@SessionAttributes("info")
public class HelloController {

    private final ClientService clientService;


    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("clientDto", new ClientDto());
        return "hello";
    }

    @PostMapping("/hello")
    public String save(@ModelAttribute("clientDto") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final ClientDto clientDto, RedirectAttributes redirectAttributes, Model model) {
        try {
            clientService.save(clientDto);
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/client2/hello";
        } catch (EntityException e) {
            model.addAttribute("error", "Termin nie jest dostępny");

            return "hello";
        }

    }


    @GetMapping("/admin")
    public String showData(Model model, final ClientDto clientDto) {
        model.addAttribute("clientDto2", clientDto);

        return "admin";
    }


    @PostMapping("/admin")
    public String saveAdmin(@ModelAttribute("clientDto") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final ClientDto clientDto, RedirectAttributes redirectAttributes) {
        try {
            clientService.save(clientDto);
            redirectAttributes.addFlashAttribute("success", true);
        } catch (EntityException e) {
            redirectAttributes.addFlashAttribute("error2", "Termin nie jest dostępny");
        }
        return "redirect:/client2/admin";
    }


    @PostMapping("/delete/code")
    public String deleteClient(@ModelAttribute("clientDto") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final ClientDto clientDto, RedirectAttributes redirectAttributes) {
        try {
            clientService.delete(clientDto.getUniqueCode());
            redirectAttributes.addFlashAttribute("successDelete", true);
        } catch (EntityException e) {
            redirectAttributes.addFlashAttribute("error3", "Nie istnieje klient o takim kodzie2");
        }
        return "redirect:/client2/admin";

    }

    @PostMapping("/admin/code")
    public String updateAdmin(@ModelAttribute("clientDto") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") final ClientDto clientDto, RedirectAttributes redirectAttributes) {
        try {
            clientService.update(clientDto);
            redirectAttributes.addFlashAttribute("successEdit", true);
        } catch (EntityException e) {
            redirectAttributes.addFlashAttribute("error2", "Nie istnieje klient o takim kodzie");
        }
        return "redirect:/client2/admin";
    }

}
