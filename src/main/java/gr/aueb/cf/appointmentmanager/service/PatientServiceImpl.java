package gr.aueb.cf.appointmentmanager.service;

import gr.aueb.cf.appointmentmanager.dto.PatientDTO;
import gr.aueb.cf.appointmentmanager.model.Patient;
import gr.aueb.cf.appointmentmanager.repository.PatientRepository;
import gr.aueb.cf.appointmentmanager.service.exceptions.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PatientServiceImpl implements IPatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public Patient createPatient(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        return patientRepository.save(patient);
    }

    @Transactional
    @Override
    public Patient updatePatient(PatientDTO patientDTO) throws EntityNotFoundException {
        Patient patient = patientRepository.findPatientById(patientDTO.getId());

        if (patient == null) {
            throw new EntityNotFoundException(Patient.class, patientDTO.getId());
        }
        modelMapper.map(patientDTO,patient);
        return patientRepository.save(patient);
    }

    @Transactional
    @Override
    public void deletePatient(Long id) throws EntityNotFoundException {
        Patient patient = patientRepository.findPatientById(id);

        if (patient == null) {
            throw new EntityNotFoundException(Patient.class,id);
        }
        patientRepository.delete(patient);
    }

    @Override
    public Patient getPatientByFullname(String firstname, String lastname) throws EntityNotFoundException {
        Patient patient = patientRepository.findByFirstnameAndLastname(firstname,lastname);

        if (patient == null) {
            throw new EntityNotFoundException(Patient.class,null);
        }
        return patient;
    }

    @Override
    public Patient getPatientById(Long id) throws EntityNotFoundException {
        Patient patient = patientRepository.findPatientById(id);

        if (patient == null) {
            throw new EntityNotFoundException(Patient.class,id);
        }
        return patient;
    }

    @Override
    public List<Patient> getAllPatients() throws EntityNotFoundException {
        List<Patient> patients = patientRepository.findAll();

        if (patients.isEmpty()) {
            throw new EntityNotFoundException(Patient.class,null);
        }
        return patients;
    }
}
