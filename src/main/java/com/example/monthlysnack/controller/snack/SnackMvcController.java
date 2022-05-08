package com.example.monthlysnack.controller.snack;

import com.example.monthlysnack.model.snack.SnackDto.UpdateSnack;
import com.example.monthlysnack.service.snack.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
public class SnackMvcController {
    private final SnackService snackService;

    public SnackMvcController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping("/snacks")
    public String getSnacksPage(Model model) {
        var snacks = snackService.getAllSnack();
        var snackCategories = snackService.getAllSnackCategory();

        model.addAttribute("snacks", snacks);
        model.addAttribute("snackCategories", snackCategories);

        return "snack/snack-list";
    }

    @GetMapping("/snacks/{name}")
    public String getSnacksPage(Model model, @PathVariable("name") String name) {
        var snacks = snackService.getByName(name);
        var snackCategories = snackService.getAllSnackCategory();

        model.addAttribute("snacks", snacks);
        model.addAttribute("snackCategories", snackCategories);

        return "snack/snack-list";
    }

    @GetMapping("/snack/{id}")
    public String getSnackDetailPage(Model model, @PathVariable("id") UUID id) {
        var snack = snackService.getById(id);
        var snackCategory =
                snackService.getCategoryById(snack.getSnackCategoryId());

        model.addAttribute("snack", snack);
        model.addAttribute("snackCategory", snackCategory);
        return "snack/snack-detail";
    }

    @GetMapping("/snack/update/{id}")
    public String getSnackUpdatePage(Model model, @PathVariable("id") UUID id) {
        var snack = snackService.getById(id);
        var snackCategories = snackService.getAllSnackCategory();

        model.addAttribute("snack", snack);
        model.addAttribute("snackCategories", snackCategories);

        return "snack/snack-update";
    }

    @PostMapping("/snack/update")
    public String updateCustomer(@RequestBody UpdateSnack updateSnack) {
        var snack = snackService.updateSnack(updateSnack);
        return "redirect:/snack/" + snack.getSnackId().toString();
    }
}
