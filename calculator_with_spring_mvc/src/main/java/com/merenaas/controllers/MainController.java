package com.merenaas.controllers;

import com.merenaas.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping("/calc")
public class MainController {
    private CalculatorService calculatorService;

    @Autowired
    public MainController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @RequestMapping(value = ("/calc"), method = RequestMethod.POST)
    public String calc(@RequestParam String a, @RequestParam String b, @RequestParam String op, ModelMap model) {
        try {
            int res = calculatorService.calc(a, b, op);
            model.addAttribute("res", res);
        } catch (NumberFormatException exception) {
            model.addAttribute("error", "Неверный формат чисел");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Неверный формат оператора. Введите +, - , * или  /");
        } catch (ArithmeticException arithmeticException) {
            model.addAttribute("error", "Арифметическая ошибка");
        }
        return "calc";
    }

    @RequestMapping(value = ("/calc"), method = RequestMethod.GET)
    public String getCalc() {
        return "calc";
    }

    @RequestMapping(value = ("/page"), method = RequestMethod.GET)
    public String getpage() {
        return "page";
    }

    @RequestMapping(value = ("/pages"), method = RequestMethod.GET)
    public String getpages() {
        return "pages";
    }

    @RequestMapping("/code/{param_special_name}")
    public String pathVariableBunch(
            @PathVariable(
                    value = "param_special_name"
            ) String param
    ) {
        return param;
    }

    @RequestMapping(value = ("/error"), method = RequestMethod.GET)
    public String getError() {
        return "error";
    }


}
