//package com.example.reminders_api.controller;
//
//import com.example.reminders_api.dto.ReminderResponse;
//import com.example.reminders_api.model.Reminder;
//import com.example.reminders_api.model.Status;
//import com.example.reminders_api.service.ReminderService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/reminders")
//@RequiredArgsConstructor
////@CrossOrigin(origins = "http://localhost:5173")
//public class ReminderController {
//
//    private final ReminderService service;
//
//    // GET /api/reminders?userName=
//    @GetMapping("/u")
//    public ResponseEntity<ReminderResponse> getAllRemindersByUser(@RequestParam String userName) {
//        var reminders = service.findAllByUserName(userName);
//        if (reminders.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT)
//                    .body(new ReminderResponse(HttpStatus.NO_CONTENT, reminders));
//        }
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ReminderResponse(HttpStatus.OK, reminders));
//    }
//
//    // GET /api/reminders?status=....
//    @GetMapping("/s")
//    public ResponseEntity<ReminderResponse> getAllRemindersByUser(@RequestParam Status status) {
//        var reminders = service.findAllByStatus(status);
//        if (reminders.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT)
//                    .body(new ReminderResponse(HttpStatus.NO_CONTENT, reminders));
//        }
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ReminderResponse(HttpStatus.OK, reminders));
//    }
//
//
//    @PostMapping
//    public ResponseEntity<ReminderResponse> createReminder(@Valid @RequestBody ReminderRequestDto request) {
//        var reminder = Reminder.builder()
//                .text(request.text()).remindOn(request.remindOn())
//                .remindMe(request.remindMe()).status(Status.PENDING).userName("juna")
//                .build();
//        reminder = service.save(reminder);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new ReminderResponse(HttpStatus.CREATED, reminder));
//    }
//
//}


package com.example.reminders_api.controller;

import com.example.reminders_api.dto.ReminderResponse;
import com.example.reminders_api.model.Reminder;
import com.example.reminders_api.model.Status;
import com.example.reminders_api.service.ReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService service;

    // GET /api/reminders?userName=...
    @GetMapping("/u")
    public ResponseEntity<ReminderResponse> getAllRemindersByUser(@RequestParam String userName) {
        var reminders = service.findAllByUserName(userName);
        if (reminders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ReminderResponse(HttpStatus.NO_CONTENT, reminders));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ReminderResponse(HttpStatus.OK, reminders));
    }

    // GET /api/reminders?status=...
    @GetMapping("/s")
    public ResponseEntity<ReminderResponse> getAllRemindersByStatus(@RequestParam Status status) {
        var reminders = service.findAllByStatus(status);
        if (reminders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ReminderResponse(HttpStatus.NO_CONTENT, reminders));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ReminderResponse(HttpStatus.OK, reminders));
    }

    // POST /api/reminders
    @PostMapping
    public ResponseEntity<ReminderResponse> createReminder(@Valid @RequestBody ReminderRequestDto request) {
        var reminder = Reminder.builder()
                .text(request.text()).remindOn(request.remindOn())
                .remindMe(request.remindMe()).status(Status.PENDING).userName("juna")
                .build();
        reminder = service.save(reminder);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ReminderResponse(HttpStatus.CREATED, reminder));
    }

    // PUT /api/reminders/{id}/complete
    @PutMapping("/{id}/complete")
    public ResponseEntity<ReminderResponse> completeReminder(@PathVariable String id) {
        var reminder = service.findById(id);
        if (reminder == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ReminderResponse(HttpStatus.NOT_FOUND, "Reminder not found"));
        }

        reminder.setStatus(Status.COMPLETE);  // Set the status to COMPLETE
        service.save(reminder);  // Save the updated reminder
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ReminderResponse(HttpStatus.OK, reminder));
    }

    // DELETE /api/reminders/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<ReminderResponse> deleteReminder(@PathVariable String id) {
        boolean isDeleted = service.deleteById(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ReminderResponse(HttpStatus.NOT_FOUND, "Reminder not found"));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ReminderResponse(HttpStatus.NO_CONTENT, "Reminder deleted successfully"));
    }
}
