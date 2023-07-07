package com.example.demoredis.Controller;

import com.example.demoredis.DTO.studentDTO;
import com.example.demoredis.MessageRabbitmq.Receiver;
import com.example.demoredis.MessageRabbitmq.Sender;
import com.example.demoredis.MessageRedis.Publisher;
import com.example.demoredis.MessageRedis.Subscriber;
import com.example.demoredis.model.Student;
import com.example.demoredis.repository.StudentRepository;
import com.example.demoredis.service.StudentService;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;


@RestController
public class StudentController {
//    private static final Logger log = LoggerFactory.getLogger("outbound-logs");
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private Publisher publisher;
    @Autowired
    private Subscriber subscriber;
    //    @Autowired
//    private cacheManager cacheManager;
    @Autowired
    private Sender sender;
    @Autowired
    private Receiver receiver;

    @PostMapping("redis/send-message")
    public ResponseEntity<String> sendMessageFromRedis(@RequestBody studentDTO student) {
        publisher.publish(student);
        return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
    }

    @GetMapping("redis/receiving-message/chanel")
    public ResponseEntity<String> receivingMessageRedis(@RequestParam("chanel") String chanel) {
        List<String> mess = subscriber.getMessagesByChannel(chanel);
        return new ResponseEntity<>("Message is received " + mess, HttpStatus.OK);
    }
    @PostMapping("rabbitmq/send-message/")
    public ResponseEntity<String> sendMessageFromRabbit(@RequestBody studentDTO studentDTO) {
      sender.send(studentDTO);
        return new ResponseEntity<>("Message sent successfully :"+studentDTO.toString(), HttpStatus.OK);
    }
    @GetMapping("rabbitmq/receiving-message/queName")
    public ResponseEntity<Object> receivingMessageRabbit(@RequestParam("queueName") String queName) throws IOException, TimeoutException {
      Object mess=  receiver.receivedWithChanel(queName);
        return new ResponseEntity<>("Message sent successfully :"+mess, HttpStatus.OK);
    }

    //    @GetMapping("/getCache/{cacheName}/{key}")
//    public ResponseEntity<String> getAllDataByKeyCache(@PathVariable String cacheName,@PathVariable String key) {
//        Object mess= cacheManager.getValueFromCache(cacheName,key);
//        return new ResponseEntity<>("Message is received " + mess.toString(), HttpStatus.OK);
//    }
    @CachePut(cacheNames = "student1", key = "#student.id")
    @PostMapping(value = "/update/{id}")
    public ResponseEntity<Object> update(@RequestBody studentDTO student, @PathVariable Long id) {

        return ResponseEntity.ok(studentService.update(student, id));
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Object> add(@RequestBody studentDTO student) {
        return ResponseEntity.ok(studentService.add(student));
    }

    @GetMapping("/get/{id}")
    @Cacheable(cacheNames = "student", key = "#id")
    public String getById(@PathVariable Long id) {
        return studentService.findById(id).toString();
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        studentService.deleteById(id);
        return new ResponseEntity<>("Delete by id :" + id, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Student> getAll() {
//        log.info("Get all student");
        return studentRepository.findAll();
    }

    @GetMapping("/getFoodetail")
    public JSONArray foodDetails() {
//        log.info("Inside Food Detail Function");
        return studentService.getAllFoodDetails();
    }
}
