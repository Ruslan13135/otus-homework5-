package ot.homework5plus.rushm.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ot.homework5plus.rushm.models.entity.Book;
import ot.homework5plus.rushm.models.entity.Instance;
import ot.homework5plus.rushm.service.InstanceService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class InstanceController {
    private static final String INSTANCE_SERVICE = "instanceService";

    private final InstanceService instanceService;

    public InstanceController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    // Получить все экземпляры
    @GetMapping("/api/instance")
    @Bulkhead(name = INSTANCE_SERVICE, fallbackMethod = "bulkHeadGetAllInstance", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<List<Instance>> getInstances() {
        return new ResponseEntity<>(instanceService.getAll(), HttpStatus.OK);
    }

    // Получить экземпляр
    @GetMapping("/api/instance/{id}")
    @Bulkhead(name = INSTANCE_SERVICE, fallbackMethod = "bulkHeadGetInstance", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<Instance> getInstances(@PathVariable("id") Long instanceId) {
        if (instanceId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Instance instance = instanceService.getInstance(instanceId);
        return new ResponseEntity<>(instance, HttpStatus.OK);
    }

    // Получить все экземпляры книги
    @GetMapping("/api/instance/book/{bookId}")
    @Bulkhead(name = INSTANCE_SERVICE, fallbackMethod = "bulkHeadGetAllInstance", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<List<Instance>> getInstancesByBookId(@PathVariable("bookId") Long bookId) {
        if (bookId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Instance> instance = instanceService.getInstancesByBookId(bookId);
        return new ResponseEntity<>(instance, HttpStatus.OK);
    }

    // Создать новый экземпляр
    @PostMapping("/api/instance")
    @Bulkhead(name = INSTANCE_SERVICE, fallbackMethod = "bulkHeadGetInstance", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<Instance> addInstance(@RequestBody Instance requestInstance) {
        if (requestInstance == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestInstance.setId(null);
        Instance instance = instanceService.addInstance(requestInstance);
        if (instance == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(instance, HttpStatus.OK);
    }

    // Обновить экземпляр
    @PutMapping("/api/instance/{id}")
    public ResponseEntity<Instance> updateInstance(@PathVariable("id") Long id, @RequestBody Instance requestInstance) {
        if (requestInstance == null || requestInstance.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestInstance.setId(id);
        if (!instanceService.instanceExists(requestInstance.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Instance instance = instanceService.updateInstance(requestInstance);
        if (instance == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(instance, HttpStatus.OK);
    }

    // Удалить экземпляр
    @DeleteMapping("/api/instance/{id}")
    @Bulkhead(name = INSTANCE_SERVICE, fallbackMethod = "bulkHeadDelInstance", type = Bulkhead.Type.SEMAPHORE)
    public ResponseEntity<String> deleteInstance(@PathVariable("id") Long instanceId) {
        if (instanceId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Instance instance = instanceService.getInstance(instanceId);
        if (instance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        instanceService.deleteInstance(instanceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<Instance>> bulkHeadGetAllInstance(Exception t) {
        log.info("bulkHeadGetAllInstance");
        List<Instance> instance = new ArrayList<>();
        instance.add(new Instance(1L, "instance", new Book()));
        return new ResponseEntity<>(instance, HttpStatus.OK);
    }

    public ResponseEntity<Instance> bulkHeadGetInstance(Exception t) {
        log.info("bulkHeadGetInstance");
        Instance instance = new Instance(1L, "instance", new Book());
        return new ResponseEntity<>(instance, HttpStatus.OK);
    }

    public ResponseEntity<String> bulkHeadDelInstance(Exception t) {
        log.info("bulkHeadDelInstance");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
