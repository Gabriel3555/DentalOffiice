package gdbv.clinica.models;

import gdbv.clinica.exceptions.DoctorException;
import gdbv.clinica.persistence.PersistenceController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Controller {

    private final PersistenceController persistenceController = new PersistenceController();

    public Object[] autenticate(String email, String password, String role) {
        return persistenceController.autenticate(email, password, role);
    }

    public Responsible registerResponsible(Responsible responsible) throws Exception {
        return persistenceController.registerResponsible(responsible);
    }

    public void updateResponsible(Responsible responsible) throws Exception {
        persistenceController.updateResponsible(responsible);
    }

    public void deleteResponsible(Long responsibleId) throws Exception {
        persistenceController.deleteResponsible(responsibleId);
    }

    public Responsible getResponsibleById(Long responsibleId) throws Exception {
        return persistenceController.getResponsibleById(responsibleId);
    }

    public List<Responsible> getAllResponsibles() throws Exception {
        return persistenceController.getAllResponsibles();
    }

    public Schedule createSchedule(Schedule schedule) throws Exception {
        return persistenceController.createSchedule(schedule);
    }

    public void updateSchedule(Schedule schedule) throws Exception {
        persistenceController.updateSchedule(schedule);
    }

    public void deleteSchedule(Long scheduleId) throws Exception {
        persistenceController.deleteSchedule(scheduleId);
    }

    public Schedule getScheduleById(Long scheduleId) throws Exception {
        return persistenceController.getScheduleById(scheduleId);
    }

    public List<Schedule> getAllSchedules() throws Exception {
        return persistenceController.getAllSchedules();
    }

    public Secretary registerSecretary(Secretary secretary) throws Exception {
        return persistenceController.registerSecretary(secretary);
    }

    public void updateSecretary(Secretary secretary) throws Exception {
        persistenceController.updateSecretary(secretary);
    }

    public void deleteSecretary(Long secretaryId) throws Exception {
        persistenceController.deleteSecretary(secretaryId);
    }

    public Secretary getSecretaryById(Long secretaryId) throws Exception {
        return persistenceController.getSecretaryById(secretaryId);
    }

    public List<Secretary> getAllSecretaries() throws Exception {
        return persistenceController.getAllSecretaries();
    }

    public User registerUser(User user) throws Exception {
        return persistenceController.registerUser(user);
    }

    public void updateUser(User user) throws Exception {
        persistenceController.updateUser(user);
    }

    public void deleteUser(Long userId) throws Exception {
        persistenceController.deleteUser(userId);
    }

    public User getUserById(Long userId) throws Exception {
        return persistenceController.getUserById(userId);
    }

    public List<User> getAllUsers() throws Exception {
        return persistenceController.getAllUsers();
    }

    public User getUserByUserName(String userName) throws Exception {
        return persistenceController.getUserByUserName(userName);
    }

    public void linkDoctorToUser(Long userId, Long doctorId) throws Exception {
        persistenceController.linkDoctorToUser(userId, doctorId);
    }

    public void linkSecretaryToUser(Long userId, Long secretaryId) throws Exception {
        persistenceController.linkSecretaryToUser(userId, secretaryId);
    }

    public Turn createTurn(Long doctorId, Long patientId, LocalDate date, LocalTime time) throws Exception {
        return persistenceController.createTurn(doctorId, patientId, date, time);
    }

    public void updateTurn(Turn turn) throws Exception {
        persistenceController.updateTurn(turn);
    }

    public void deleteTurn(Long turnId) throws Exception {
        persistenceController.deleteTurn(turnId);
    }

    public Turn getTurnById(Long turnId) throws Exception {
        return persistenceController.getTurnById(turnId);
    }

    public List<Turn> getTurnsByIdDoctor(Long id) throws Exception {
        return persistenceController.getTurnsByIdDoctor(id);
    }

    public List<Turn> getAllTurns() throws Exception {
        return persistenceController.getAllTurns();
    }

    public Patient registerPatient(Patient patient, Responsible responsible) throws Exception {
        return persistenceController.registerPatient(patient, responsible);
    }

    public void updatePatient(Patient patient) throws Exception {
        persistenceController.updatePatient(patient);
    }

    public void deletePatient(Long patientId) throws Exception {
        persistenceController.deletePatient(patientId);
    }

    public int getPatientAge(Long patientId) throws Exception {
        return persistenceController.getPatientAge(patientId);
    }

    public List<Patient> searchPatientsByAgeRange(int minAge, int maxAge) throws Exception {
        return persistenceController.searchPatientsByAgeRange(minAge, maxAge);
    }

    public List<Turn> getPatientTurns(Long patientId) throws Exception {
        return persistenceController.getPatientTurns(patientId);
    }

    public Map<String, Long> getInsuranceStatistics() throws Exception {
        return persistenceController.getInsuranceStatistics();
    }

    public List<Patient> getMinorPatients() throws Exception {
        return persistenceController.getMinorPatients();
    }

    public boolean isMinor(Long patientId) throws Exception {
        return persistenceController.isMinor(patientId);
    }

    public Doctor registerDoctor(Doctor doctor, Schedule schedule) throws DoctorException, DoctorException {
        return persistenceController.registerDoctor(doctor, schedule);
    }

    public void updateDoctor(Doctor doctor) throws DoctorException {
        persistenceController.updateDoctor(doctor);
    }

    public void deleteDoctor(Long doctorId) throws DoctorException {
        persistenceController.deleteDoctor(doctorId);
    }

    public Schedule getDoctorSchedule(Long doctorId) throws DoctorException {
        return persistenceController.getDoctorSchedule(doctorId);
    }

    public boolean isDoctorAvailable(Long doctorId, LocalDate date, LocalTime time) throws DoctorException {
        return persistenceController.isDoctorAvailable(doctorId, date, time);
    }

    public List<Turn> getDoctorTurns(Long doctorId, LocalDate date) throws DoctorException {
        return persistenceController.getDoctorTurns(doctorId, date);
    }
}
