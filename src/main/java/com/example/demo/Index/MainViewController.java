package com.example.demo.Index;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "index")
public class MainViewController {


    @GetMapping
    public String getImagesList(Model model){
        return "index";
    }
}
