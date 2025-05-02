import React, { useState } from 'react';
import ReminderForm from './components/ReminderForm'; 
import ReminderList from './components/ReminderList'; 
import './index.css'

const App = () => {
  const [reminders, setReminders] = useState([]);

  // Function to fetch reminders and update state in the parent component
  const fetchReminders = () => {
    // Fetch reminders by user name (you can customize this based on your requirement)
    const userName = 'juna'; // Example userName
    fetch(`http://localhost:9000/api/reminders/u?userName=${userName}`)
      .then((response) => response.json())
      .then((data) => setReminders(data.payload)) // Assuming payload contains the list of reminders
      .catch((error) => console.error('Error fetching reminders:', error));
  };

  return (
    <div className="App">
      <header className="bg-blue-600 text-Black p-4 text-center">
        <h1 className="text-3xl font-bold">Reminder App</h1>
      </header>

      <main className="p-6">
        {/* Reminder Form */}
        <ReminderForm fetchReminders={fetchReminders} />
        
        {/* Reminder List */}
        <ReminderList reminders={reminders} fetchReminders={fetchReminders} />
      </main>
    </div>
  );
};

export default App;
