package gdbv.clinica.persistence;

import gdbv.clinica.exceptions.DoctorException;
import gdbv.clinica.models.*;
import gdbv.clinica.services.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class PersistenceController {

    public Object[] autenticate(String email, String password, String role) {
        LoginService loginService = new LoginService();
        return loginService.autenticate(email, password, role);
    }

    private final ResponsibleService responsibleService = new ResponsibleService();

    public Responsible registerResponsible(Responsible responsible) throws Exception {
        return responsibleService.registerResponsible(responsible);
    }

    public void updateResponsible(Responsible responsible) throws Exception {
        responsibleService.updateResponsible(responsible);
    }

    public void deleteResponsible(Long responsibleId) throws Exception {
        responsibleService.deleteResponsible(responsibleId);
    }

    public Responsible getResponsibleById(Long responsibleId) throws Exception {
        return responsibleService.getResponsibleById(responsibleId);
    }

    public List<Responsible> getAllResponsibles() throws Exception {
        return responsibleService.getAllResponsibles();
    }

    private final ScheduleService scheduleService = new ScheduleService();

    public Schedule createSchedule(Schedule schedule) throws Exception {
        return scheduleService.createSchedule(schedule);
    }

    public void updateSchedule(Schedule schedule) throws Exception {
        scheduleService.updateSchedule(schedule);
    }

    public void deleteSchedule(Long scheduleId) throws Exception {
        scheduleService.deleteSchedule(scheduleId);
    }

    public Schedule getScheduleById(Long scheduleId) throws Exception {
        return scheduleService.getScheduleById(scheduleId);
    }

    public List<Schedule> getAllSchedules() throws Exception {
        return scheduleService.getAllSchedules();
    }

    private final SecretaryService secretaryService = new SecretaryService();

    public Secretary registerSecretary(Secretary secretary) throws Exception {
        return secretaryService.registerSecretary(secretary);
    }

    public void updateSecretary(Secretary secretary) throws Exception {
        secretaryService.updateSecretary(secretary);
    }

    public void deleteSecretary(Long secretaryId) throws Exception {
        secretaryService.deleteSecretary(secretaryId);
    }

    public Secretary getSecretaryById(Long secretaryId) throws Exception {
        return secretaryService.getSecretaryById(secretaryId);
    }

    public List<Secretary> getAllSecretaries() throws Exception {
        return secretaryService.getAllSecretaries();
    }

    private final UserService userService = new UserService();

    public User registerUser(User user) throws Exception {
        return userService.registerUser(user);
    }

    public void updateUser(User user) throws Exception {
        userService.updateUser(user);
    }

    public void deleteUser(Long userId) throws Exception {
        userService.deleteUser(userId);
    }

    public User getUserById(Long userId) throws Exception {
        return userService.getUserById(userId);
    }

    public List<User> getAllUsers() throws Exception {
        return userService.getAllUsers();
    }

    public User getUserByUserName(String userName) throws Exception {
        return userService.getUserByUserName(userName);
    }


    public void linkDoctorToUser(Long userId, Long doctorId) throws Exception {
        userService.linkDoctorToUser(userId, doctorId);
    }

    public void linkSecretaryToUser(Long userId, Long secretaryId) throws Exception {
        userService.linkSecretaryToUser(userId, secretaryId);
    }

    private final TurnService turnService = new TurnService();

    public Turn createTurn(Long doctorId, Long patientId, LocalDate date, LocalTime time) throws Exception {
        return turnService.createTurn(doctorId, patientId, date, time);
    }

    public void updateTurn(Turn turn) throws Exception {
        turnService.updateTurn(turn);
    }

    public void deleteTurn(Long turnId) throws Exception {
        turnService.deleteTurn(turnId);
    }

    public Turn getTurnById(Long turnId) throws Exception {
        return turnService.getTurnById(turnId);
    }

    public List<Turn> getTurnsByIdDoctor(Long id) throws Exception {
        return turnService.getTurnsByDoctor(id);
    }

    public List<Turn> getAllTurns() throws Exception {
        return turnService.getAllTurns();
    }

    private final PatientService patientService = new PatientService();

    public Patient registerPatient(Patient patient, Responsible responsible) throws Exception {
        return patientService.registerPatient(patient, responsible);
    }

    public void updatePatient(Patient patient) throws Exception {
        patientService.updatePatient(patient);
    }

    public void deletePatient(Long patientId) throws Exception {
        patientService.deletePatient(patientId);
    }

    public int getPatientAge(Long patientId) throws Exception {
        return patientService.getPatientAge(patientId);
    }

    public List<Patient> searchPatientsByAgeRange(int minAge, int maxAge) throws Exception {
        return patientService.searchPatientsByAgeRange(minAge, maxAge);
    }

    public List<Turn> getPatientTurns(Long patientId) throws Exception {
        return patientService.getPatientTurns(patientId);
    }

    public Map<String, Long> getInsuranceStatistics() throws Exception {
        return patientService.getInsuranceStatistics();
    }

    public List<Patient> getMinorPatients() throws Exception {
        return patientService.getMinorPatients();
    }

    public boolean isMinor(Long patientId) throws Exception {
        return patientService.isMinor(patientId);
    }

    private final DoctorService doctorService = new DoctorService();

    public Doctor registerDoctor(Doctor doctor, Schedule schedule) throws DoctorException {
        return doctorService.registerDoctor(doctor, schedule);
    }

    public void updateDoctor(Doctor doctor) throws DoctorException {
        doctorService.updateDoctor(doctor);
    }

    public void deleteDoctor(Long doctorId) throws DoctorException {
        doctorService.deleteDoctor(doctorId);
    }

    public Schedule getDoctorSchedule(Long doctorId) throws DoctorException {
        return doctorService.getDoctorSchedule(doctorId);
    }

    public boolean isDoctorAvailable(Long doctorId, LocalDate date, LocalTime time) throws DoctorException {
        return doctorService.isDoctorAvailable(doctorId, date, time);
    }

    public List<Turn> getDoctorTurns(Long doctorId, LocalDate date) throws DoctorException {
        return doctorService.getDoctorTurns(doctorId, date);
    }
}
