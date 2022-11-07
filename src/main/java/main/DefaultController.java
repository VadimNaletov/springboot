package main;

import main.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController {
    @Autowired
    private TaskRepository taskRepository;
    @RequestMapping("/")
    public String index(Model model){
        Iterable<Task> taskIterable = taskRepository.findAll();
        List<Task> tasks = new ArrayList<>();
        for (Task task : taskIterable){
            tasks.add(task);
        }
        model.addAttribute("tasks", tasks);
        return "index";
    }

}
