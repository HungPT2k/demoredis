package com.example.demoredis.service;

import com.example.demoredis.DTO.studentDTO;
import com.example.demoredis.model.Student;
import com.example.demoredis.repository.StudentRepository;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@Service
public class StudentService {
//    private static final Logger log= LoggerFactory.getLogger("outbound-logs");
    public static final String hashName = "ITEM";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StudentRepository studentRepository;
//    @Value("${redis.student.topic}")
//    private String messageTopic;

 //   @CachePut(cacheNames = "student",key = "#student.id")
    public studentDTO update(studentDTO student, Long id){
        Optional<Student>  a=studentRepository.findById(id);
        if(a.isEmpty()) return null;
            a.get().setDob(student.getDob());
            a.get().setGender(student.getGender());
            a.get().setName(student.getName());
            studentRepository.save(a.get());
            return student;
        }
  //  @Cacheable(cacheNames = "student", key = "#id")
    public studentDTO findById(Long id){
      Optional<Student> student=studentRepository.findById(id);
      if(student.isEmpty()) throw new RuntimeException("Not is already");
        studentDTO a = new studentDTO();
        a.setDob(student.get().getDob());
        a.setGender(student.get().getGender());
        a.setName(student.get().getName());
        return a;
    }
    public studentDTO add(studentDTO studentDTO){
        Student a = new Student();
        a.setDob(studentDTO.getDob());
        a.setGender(studentDTO.getGender());
        a.setName(studentDTO.getName());
        studentRepository.save(a);
        return studentDTO;
    }
    public void deleteById(Long id){
        studentRepository.deleteById(id);
        System.out.println("delete id " + id);
    }


    public JSONArray getAllFoodDetails(){
//        log.info("Fetching ALL food details...");
        JSONArray foodDetail = new JSONArray();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("example.json", StandardCharsets.UTF_8));
            JSONObject jsonObject = (JSONObject) obj;
            foodDetail = (JSONArray) jsonObject.get("data");

        } catch (IOException | ParseException e) {
//            log.error("Error occurred in reading JSON file");
            e.printStackTrace();
        }
        return foodDetail;
    }

}
