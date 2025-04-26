package com.beautysalon.service;

import com.beautysalon.dto.AppointmentInfoDTO;
import com.beautysalon.model.Appointment;
import com.beautysalon.model.Master;
import com.beautysalon.repository.AppointmentRepository;
import com.beautysalon.repository.MasterRepository;
import com.beautysalon.repository.ServiceRepository;
import com.beautysalon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private MasterRepository masterRepository;

    @Autowired
    private UserRepository userRepository;

    public List<AppointmentInfoDTO> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();

        return appointments.stream().map(appointment -> {
            String serviceName = serviceRepository.findById(appointment.getServiceId())
                    .map(s -> s.getName()).orElse("неизвестно");

            String masterName = masterRepository.findById(appointment.getMasterId())
                    .map(m -> m.getName()).orElse("неизвестно");

            String clientEmail = userRepository.findById(appointment.getUserId())
                    .map(u -> u.getEmail()).orElse("неизвестно");

            return new AppointmentInfoDTO(
                    appointment.getId(),
                    serviceName,
                    masterName,
                    appointment.getDate().toString(),
                    appointment.getTime().toString(),
                    clientEmail
            );
        }).toList();
    }

    public List<AppointmentInfoDTO> getAppointmentsForMaster(String masterId) {
        List<Appointment> appointments = appointmentRepository.findByMasterId(masterId);

        return appointments.stream().map(appointment -> {
            String serviceName = serviceRepository.findById(appointment.getServiceId())
                    .map(s -> s.getName()).orElse("неизвестно");

            String masterName = masterRepository.findById(appointment.getMasterId())
                    .map(m -> m.getName()).orElse("неизвестно");

            String clientEmail = userRepository.findById(appointment.getUserId())
                    .map(u -> u.getEmail()).orElse("неизвестно");

            return new AppointmentInfoDTO(
                    appointment.getId(),
                    serviceName,
                    masterName,
                    appointment.getDate().toString(),
                    appointment.getTime().toString(),
                    clientEmail
            );
        }).toList();
    }
}
