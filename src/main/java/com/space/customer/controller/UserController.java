package com.space.customer.controller;

import com.space.customer.model.User;
import com.space.customer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin("http://localhost:4200")
public class  UserController {

    @Autowired
    private UserService service;

    @GetMapping("/user")
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @PostMapping("/userAdd")
    public User insertUser(@RequestBody User user){
        return service.insertUser(user);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser (@PathVariable long id ,@RequestBody User newUser){
        String response = service.updateUserById(id,newUser);
        if(response.contains("successfully")){
            return ResponseEntity.ok(response);
        } else if (response.contains("not found")) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id){
        try {
            service.deleteUser(id);
            return new ResponseEntity<>("Item Deleted Successfully",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to delete User",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @GetMapping("/getId/{id}")
   public ResponseEntity<User> getUserById(@PathVariable long id){
        User user = service.findByID(id);
        return ResponseEntity.ok(user);
   }

    @GetMapping("/getEmail/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        User user = service.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody User user){
        return service.signUP(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String response = service.login(email, password);
        if (response.equals("Login successful")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }


    static class LoginRequest {
        private String email;
        private String password;


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PutMapping("/changePassword/{id}")
    public ResponseEntity<String> changePassword(@PathVariable("id") long id, @RequestBody String newPassword) {
        boolean passwordChanged = service.changePassword(id, newPassword);
            return ResponseEntity.ok("Password changed successfully.");
        }
    }


