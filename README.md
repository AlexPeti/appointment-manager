AppointMedCF

![login](https://github.com/AlexPeti/appointment-manager/assets/110426010/9cf50e5a-d068-4a3f-9833-95911d5bd924)

Description:

This is the final project for the Athens University of Economics and Business, Coding Factory 3 program. It was visualized as an appointment application used by clinics to manage doctor appointments. The application provides features such as user registration and login, a dashboard to view and manage doctors, the ability to add or delete doctors, and the option to book appointments with doctors. Users can also check a doctor's schedule to see their appointments and update or delete them. The application was built using Java, Spring Boot 2, Spring Data JPA, Spring Security, Thymeleaf, jBCrypt, Spring Boot Validation, ModelMapper, and Bootstrap.

Technologies used: 

    Java 11
    Spring Boot 2
    Spring Data JPA
    Spring Security
    Spring Boot Validation
    MySQL
    Thymeleaf
    jBcrypt
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

![login](https://github.com/AlexPeti/appointment-manager/assets/110426010/eb24d813-a020-402d-a961-65a0a1f7ffac)

Registration page:

![register](https://github.com/AlexPeti/appointment-manager/assets/110426010/cf2089ed-b316-47bc-89e1-62c76c3aa755)

The dashboard page where we can select a doctor and book an appointment, check his schedule, delete him, or create a new doctor:

![dashboard](https://github.com/AlexPeti/appointment-manager/assets/110426010/dfbd3c16-d2b7-43b5-b80d-7309981c4c72)

The form used to book a new appointment:

![booking-appointment](https://github.com/AlexPeti/appointment-manager/assets/110426010/7792cf55-194e-4d79-a6f9-cf49af091264)

Filling the form:

![example](https://github.com/AlexPeti/appointment-manager/assets/110426010/257ac60e-76fa-4367-af0d-07dd29d09814)

The modal containing the appointment details if the booking process is successful:

![modal](https://github.com/AlexPeti/appointment-manager/assets/110426010/52a9fb7b-69a8-4dab-a450-f4e73dd51527)

Checking a doctor's schedule:

![schedule-page](https://github.com/AlexPeti/appointment-manager/assets/110426010/effcd1ae-c354-41fb-963a-6ab5e4caf755)
