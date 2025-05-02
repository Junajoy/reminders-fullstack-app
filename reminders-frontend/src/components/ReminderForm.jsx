import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import axios from 'axios';

const ReminderForm = ({ fetchReminders }) => {
  const formik = useFormik({
    initialValues: {
      text: '',
      remindOn: '',
      remindMe: false,
    },
    validationSchema: Yup.object({
      text: Yup.string().required('Reminder text is required'),
      remindOn: Yup.date().required('Date is required').min(new Date(), 'Date must be in the future'),
    }),
    onSubmit: async (values) => {
      try {
        // Send POST request to create a reminder
        const response = await axios.post('http://localhost:9000/api/reminders', {
          text: values.text,
          remindOn: values.remindOn,
          remindMe: values.remindMe,
        });
        console.log('Reminder created:', response.data.payload);
        fetchReminders(); // Fetch updated reminders after creation
      } catch (error) {
        console.error('Error creating reminder:', error);
      }
    },
  });

  return (
    <div className="max-w-md mx-auto mt-8 p-6 border rounded-lg shadow-lg">
      <h2 className="text-2xl font-semibold mb-4">Create a Reminder</h2>
      <form onSubmit={formik.handleSubmit}>
        <div className="mb-4">
          <label htmlFor="text" className="block text-sm font-medium text-gray-700">
            Reminder Text
          </label>
          <input
            type="text"
            id="text"
            name="text"
            className="mt-1 p-2 w-full border border-gray-300 rounded-md"
            onChange={formik.handleChange}
            value={formik.values.text}
          />
          {formik.errors.text && formik.touched.text && (
            <div className="text-sm text-red-600">{formik.errors.text}</div>
          )}
        </div>

        <div className="mb-4">
          <label htmlFor="remindOn" className="block text-sm font-medium text-gray-700">
            Remind On (Date)
          </label>
          <input
            type="date"
            id="remindOn"
            name="remindOn"
            className="mt-1 p-2 w-full border border-gray-300 rounded-md"
            onChange={formik.handleChange}
            value={formik.values.remindOn}
          />
          {formik.errors.remindOn && formik.touched.remindOn && (
            <div className="text-sm text-red-600">{formik.errors.remindOn}</div>
          )}
        </div>

        <div className="mb-4">
          <label className="inline-flex items-center">
            <input
              type="checkbox"
              name="remindMe"
              className="form-checkbox"
              onChange={formik.handleChange}
              checked={formik.values.remindMe}
            />
            <span className="ml-2">Remind Me</span>
          </label>
        </div>

        <button
          type="submit"
          className="w-full py-2 px-4 bg-blue-500 text-white rounded-md"
        >
          Submit
        </button>
      </form>
    </div>
  );
};

export default ReminderForm;
