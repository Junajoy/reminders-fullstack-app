package com.example.reminders_api.service;
//
//import com.example.reminders_api.model.Reminder;
//import com.example.reminders_api.model.Status;
//import com.example.reminders_api.repository.ReminderRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ReminderServiceImpl implements ReminderService {
//
//    private final ReminderRepository repository;
//
//    public ReminderServiceImpl(ReminderRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public List<Reminder> findAllByUserName(String userName) {
//        return repository.findAllByUserName(userName);
//    }
//
//    @Override
//    public Reminder save(Reminder reminder) {
//        return repository.save(reminder);
//    }
//
//    @Override
//    public List<Reminder> findAllByStatus(Status status) {
//        return repository.findAllByStatus(status);
//    }
//
//    /**
//     * Updates the status of a reminder by its ID.
//     *
//     * @param id The ID of the reminder to update.
//     * @param status The new status to set.
//     * @return The updated reminder object.
//     * @throws ReminderNotFoundException If the reminder with the given ID is not found.
//     */
//    @Override
//    public Reminder updateReminderStatus(String id, Status status) {
//        // Fetch the reminder by ID
//        Optional<Reminder> reminderOpt = repository.findById(id);
//
//        if (!reminderOpt.isPresent()) {
//            // If the reminder is not found, throw an exception
//            throw new ReminderNotFoundException("Reminder with ID " + id + " not found.");
//        }
//
//        // Get the reminder and update its status
//        Reminder reminder = reminderOpt.get();
//        reminder.setStatus(status);
//
//        // Save the updated reminder
//        return repository.save(reminder);
//    }
//
//    /**
//     * Custom exception to be used when a reminder is not found by ID.
//     */
//    public static class ReminderNotFoundException extends RuntimeException {
//        public ReminderNotFoundException(String message) {
//            super(message);
//        }
//    }
//}



import com.example.reminders_api.model.Reminder;
import com.example.reminders_api.model.Status;
import com.example.reminders_api.repository.ReminderRepository;
import com.example.reminders_api.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;

    // Find all reminders by user name
    @Override
    public List<Reminder> findAllByUserName(String userName) {
        return reminderRepository.findAllByUserName(userName);
    }

    // Find all reminders by status
    @Override
    public List<Reminder> findAllByStatus(Status status) {
        return reminderRepository.findAllByStatus(status);
    }

    // Find reminder by ID
    @Override
    public Reminder findById(String id) {
        Optional<Reminder> reminderOpt = reminderRepository.findById(id);
        return reminderOpt.orElse(null);  // Return null if not found
    }

    // Save or update a reminder (used for create and update/complete)
    @Override
    public Reminder save(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    // Delete reminder by ID
    @Override
    public boolean deleteById(String id) {
        if (reminderRepository.existsById(id)) {
            reminderRepository.deleteById(id);
            return true;
        }
        return false;  // Return false if reminder doesn't exist
    }
}

