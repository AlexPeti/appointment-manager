package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.DoctorDTO;
import gr.aueb.cf.appointmentmanager.model.Appointment;
import gr.aueb.cf.appointmentmanager.model.Doctor;
import gr.aueb.cf.appointmentmanager.repository.AppointmentRepository;
import gr.aueb.cf.appointmentmanager.repository.DoctorRepository;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public Doctor createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
        return doctorRepository.save(doctor);
    }

    @Transactional
    @Override
    public Doctor updateDoctor(DoctorDTO doctorDTO) throws EntityNotFoundException {
        Doctor doctor = doctorRepository.findDoctorById(doctorDTO.getId());

        if (doctor == null) {
            throw new EntityNotFoundException(Doctor.class,doctorDTO.getId());
        }
        modelMapper.map(doctorDTO,doctor);
        return doctorRepository.save(doctor);
    }

    @Transactional
    @Override
    public void deleteDoctor(Long id) throws EntityNotFoundException {
        Doctor doctor = doctorRepository.findDoctorById(id);

        if (doctor == null) {
            throw new EntityNotFoundException(Doctor.class,id);
        }

        // Delete doctor's appointments before deleting the doctor
        List<Appointment> appointments = appointmentRepository.findAllByDoctorId(id);
        appointmentRepository.deleteAll(appointments);

        doctorRepository.delete(doctor);
    }

    @Override
    public Doctor getDoctorByLastname(String lastname) throws EntityNotFoundException {
        Optional<Doctor> doctor = doctorRepository.findByLastname(lastname);

        if (doctor.isEmpty()) {
            throw new EntityNotFoundException(Doctor.class,null);
        }
        return doctor.get();
    }

    @Override
    public Doctor getDoctorById(Long id) throws EntityNotFoundException {
        Doctor doctor = doctorRepository.findDoctorById(id);

        if (doctor == null) {
            throw new EntityNotFoundException(Doctor.class,id);
        }
        return doctor;
    }

    @Override
    public List<Doctor> getAllDoctors() throws EntityNotFoundException {
        List<Doctor> doctors = doctorRepository.findAll();

        if (doctors.isEmpty()) {
            throw new EntityNotFoundException(Doctor.class,null);
        }
        return doctors;
    }
}
