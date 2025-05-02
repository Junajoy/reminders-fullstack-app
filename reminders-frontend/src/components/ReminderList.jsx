// import React, { useState, useEffect } from 'react';
// import axios from 'axios';
// import '../index.css'

// const ReminderList = () => {
//   const [reminders, setReminders] = useState([]);
//   const [statusFilter, setStatusFilter] = useState('PENDING'); // Default to PENDING
//   const [userName, setUserName] = useState('juna'); // Default to 'juna'

//   // Fetch reminders by user name
//   const fetchReminders = async () => {
//     try {
//       const response = await axios.get(`http://localhost:9000/api/reminders/u?userName=${userName}`);
//       setReminders(response.data.payload); // Assuming payload contains the list of reminders
//     } catch (error) {
//       console.error('Error fetching reminders:', error);
//     }
//   };

//   // Fetch reminders by status
//   const fetchRemindersByStatus = async () => {
//     try {
//       const response = await axios.get(`http://localhost:9000/api/reminders/s?status=${statusFilter}`);
//       setReminders(response.data.payload); // Assuming payload contains the list of reminders
//     } catch (error) {
//       console.error('Error fetching reminders by status:', error);
//     }
//   };

//   useEffect(() => {
//     fetchReminders(); // Fetch reminders on initial load
//   }, []);

//   useEffect(() => {
//     fetchRemindersByStatus(); // Fetch reminders based on status filter
//   }, [statusFilter]);

//   return (
//     <div className="mt-8">
//       <h2 className="text-2xl font-semibold mb-4">Your Reminders</h2>

//       <div className="mb-4">
//         <label htmlFor="status" className="block text-sm font-medium text-gray-700">Filter by Status</label>
//         <select
//           id="status"
//           value={statusFilter}
//           onChange={(e) => setStatusFilter(e.target.value)}
//           className="mt-1 p-2 w-full border border-gray-300 rounded-md"
//         >
//           <option value="PENDING">PENDING</option>
//           <option value="COMPLETE">COMPLETE</option>
//         </select>
//       </div>

//       <ul className="space-y-4">
//         {reminders.map((reminder) => (
//           <li key={reminder.id} className="p-4 border border-gray-200 rounded-lg">
//             <h3 className="text-lg font-semibold">{reminder.text}</h3>
//             <p className="text-sm">User: {reminder.userName}</p>
//             <p className="text-sm">Date: {new Date(reminder.remindOn).toLocaleDateString()}</p>
//             <p className="text-sm">Remind Me: {reminder.remindMe ? 'Yes' : 'No'}</p>
//             <p className="text-sm">Status: {reminder.status}</p>
//           </li>
//         ))}
//       </ul>
//     </div>
//   );
// };

// export default ReminderList;


import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../index.css';

const ReminderList = () => {
  const [reminders, setReminders] = useState([]);
  const [statusFilter, setStatusFilter] = useState('PENDING'); // Default to PENDING
  const [userName, setUserName] = useState('juna'); // Default to 'juna'

  // Fetch reminders by user name
  const fetchReminders = async () => {
    try {
      const response = await axios.get(`http://localhost:9000/api/reminders/u?userName=${userName}`);
      setReminders(response.data.payload); // Assuming payload contains the list of reminders
    } catch (error) {
      console.error('Error fetching reminders:', error);
    }
  };

  // Fetch reminders by status
  const fetchRemindersByStatus = async () => {
    try {
      const response = await axios.get(`http://localhost:9000/api/reminders/s?status=${statusFilter}`);
      setReminders(response.data.payload); // Assuming payload contains the list of reminders
    } catch (error) {
      console.error('Error fetching reminders by status:', error);
    }
  };

  // Handle completing a reminder
  const handleComplete = async (id) => {
    try {
      await axios.put(`http://localhost:9000/api/reminders/${id}/complete`);
      fetchReminders(); // Refetch reminders after completing one
    } catch (error) {
      console.error('Error completing reminder:', error);
    }
  };

  // Handle deleting a reminder
  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:9000/api/reminders/${id}`);
      fetchReminders(); // Refetch reminders after deletion
    } catch (error) {
      console.error('Error deleting reminder:', error);
    }
  };

  useEffect(() => {
    fetchReminders(); // Fetch reminders on initial load
  }, []);

  useEffect(() => {
    fetchRemindersByStatus(); // Fetch reminders based on status filter
  }, [statusFilter]);

  return (
    <div className="mt-8">
      <h2 className="text-2xl font-semibold mb-4">Your Reminders</h2>

      <div className="mb-4">
        <label htmlFor="status" className="block text-sm font-medium text-gray-700">Filter by Status</label>
        <select
          id="status"
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
          className="mt-1 p-2 w-full border border-gray-300 rounded-md"
        >
          <option value="PENDING">PENDING</option>
          <option value="COMPLETE">COMPLETE</option>
        </select>
      </div>

      <ul className="space-y-4">
        {reminders.map((reminder) => (
          <li key={reminder.id} className="p-4 border border-gray-200 rounded-lg">
            <h3 className="text-lg font-semibold">{reminder.text}</h3>
            <p className="text-sm">User: {reminder.userName}</p>
            <p className="text-sm">Date: {new Date(reminder.remindOn).toLocaleDateString()}</p>
            <p className="text-sm">Remind Me: {reminder.remindMe ? 'Yes' : 'No'}</p>
            <p className="text-sm">Status: {reminder.status}</p>

            <div className="mt-2 flex space-x-2">
              {/* Complete button */}
              {reminder.status === 'PENDING' && (
                <button
                  onClick={() => handleComplete(reminder.id)}
                  className="px-4 py-2 bg-blue-500 text-white rounded-md"
                >
                  Complete
                </button>
              )}

              {/* Delete button */}
              <button
                onClick={() => handleDelete(reminder.id)}
                className="px-4 py-2 bg-red-500 text-white rounded-md"
              >
                Delete
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ReminderList;
