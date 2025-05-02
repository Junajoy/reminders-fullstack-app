package com.example.reminders_api.service;

import com.example.reminders_api.model.Reminder;
import com.example.reminders_api.model.Status;

import java.util.List;

public interface ReminderService {

    /**
     * Find all reminders by the username.
     *
     * @param userName The username to filter reminders by.
     * @return A list of reminders associated with the given username.
     */
    List<Reminder> findAllByUserName(String userName);

    /**
     * Save a new reminder or update an existing reminder.
     *
     * @param reminder The reminder to save or update.
     * @return The saved or updated reminder object.
     */
    Reminder save(Reminder reminder);

    /**
     * Find all reminders by their status.
     *
     * @param status The status to filter reminders by.
     * @return A list of reminders with the given status.
     */
    List<Reminder> findAllByStatus(Status status);

    /**
     * Update the status of an existing reminder.
     *
     * @param id The ID of the reminder to update.
     * @param status The new status to set.
     * @return The updated reminder.
     * @throws ReminderNotFoundException If no reminder is found with the given ID.
     */
   // Reminder updateReminderStatus(String id, Status status);
    public Reminder findById(String id);

    public boolean deleteById(String id);
}
