# Classes
___
1. Worker
    * Fields 
        * Name
        * Position
        * WorkerID
    * Methods
        * getTimetable
2. Patient
    * Fields
        * Name
        * PatientID
    * Methods
        * getTimetable
3. Calendar
   * Fields
      * Day[] array
   * Methods
      * getDoctorTimetableByID
      * getPatientTimetableByID
4. Day
   * Fields
      * Appointment[] array
   * Methods
      * detAppointmentsByDoctorID
      * detAppointmentsByPatientID
5. Appointment
   * Fields
      * StartTime
      * EndTime
      * DoctorID
      * PatientID
   * Methods
      * getInfo