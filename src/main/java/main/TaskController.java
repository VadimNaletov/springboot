package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
//    private final Storage storage;
//    @Autowired
//    public TaskController(Storage storage){
//        this.storage = storage;
//    }
    @GetMapping("/tasks/")
    public List<Task> list(){
        Iterable<Task> taskIterable = taskRepository.findAll();
        List<Task> toDoTasks = new ArrayList<>();
        for(Task task : taskIterable){
            toDoTasks.add(task);
        }
        return toDoTasks;
    }

    @PostMapping("/tasks/")
    public int add(Task task){
        Task newTask = taskRepository.save(task);
        return newTask.getId();
    }
    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable(name = "id") int id){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if(!taskOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(taskOptional.get(), HttpStatus.OK);
    }
    @DeleteMapping("/tasks/")
    public void deleteAll(){
        taskRepository.deleteAll();
    }
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") int id){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if(!taskOptional.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        taskRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping("/tasks/{id}")
    public ResponseEntity update(@PathVariable(name = "id") int id, @RequestBody Task newTask){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
        taskRepository.deleteById(id);
        taskRepository.save(newTask);
        return new ResponseEntity(HttpStatus.OK);
    }

}
