Title: AppointMedCF

Description:

This is the final project for the Athens University of Economics and Business, Coding Factory 3 program. It was visualized as an appointment application used by clinics to book appointments with users to manage doctor appointments. The application provides features such as user registration and login, a dashboard to view and manage doctors, the ability to add or delete doctors, and the option to book appointments with doctors. Users can also check a doctor's schedule to see their appointments and update or delete them. The application was built using Java, Spring Boot 2, Spring Data JPA, Spring Security, Thymeleaf, jBCrypt, Spring Boot Validation, ModelMapper, and Bootstrap.

Technologies used: 

Java 11
Spring Boot 2
Spring Data JPA
Spring Security
Spring Boot Validation
MySQL
Thymeleaf
jBCrypt
ModelMapper
Bootstrap

Key Features:

User Registration and Login:
Users can create an account by registering with their details.
User authentication is implemented using Spring Security.
User credentials are securely stored in the database, and passwords are hashed using the BCrypt algorithm for enhanced security.

Doctor Dashboard:
The application provides a dashboard for users to view and manage doctors.
The dashboard displays a list of doctors with their relevant information.
Doctors can be added or deleted from the system.

Doctor Appointment Booking:
Users can book appointments with doctors through the application.
The booking process involves selecting a doctor, and providing the patient details and a date and time for the appointment.
Validation is implemented to ensure the correctness of user inputs during the booking process.
If the booking process is successful a modal with the appointment details appears.

Doctor Schedule Management:
Users can check a doctor's schedule to view their existing appointments.
The application allows users to update or delete appointments if needed.

Log-in page:

![login](https://github.com/AlexPeti/appointment-manager/assets/110426010/68a5ff0f-8d88-42ea-945d-ff84324e2a6e)

Registration page:

![registration](https://github.com/AlexPeti/appointment-manager/assets/110426010/c10a5b1b-543b-4cfe-8773-97371a363ce6)

The dashboard page where we can select a doctor and book an appointment, check his schedule, delete him, or create a new doctor:

![dashboard](https://github.com/AlexPeti/appointment-manager/assets/110426010/dfbd3c16-d2b7-43b5-b80d-7309981c4c72)

The form used to book a new appointment:

![booking-appointment](https://github.com/AlexPeti/appointment-manager/assets/110426010/2cedf585-0dee-4744-a8c4-2e3f23b19884)

Filling the form:

![book-appoint-example](https://github.com/AlexPeti/appointment-manager/assets/110426010/dff578fc-d529-44c6-8621-a3b8d5c736ce)

The modal containing the appointment details if the booking process is successful:

![success-modal](https://github.com/AlexPeti/appointment-manager/assets/110426010/408eebfb-a12d-4196-82f8-5dab2ec0e6ac)

Checking a doctor's schedule:

![schedule-page](https://github.com/AlexPeti/appointment-manager/assets/110426010/6054e5c6-ef22-4b95-91a9-46f6d6613d8c)



