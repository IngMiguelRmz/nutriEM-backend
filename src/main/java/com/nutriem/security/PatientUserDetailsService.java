package com.nutriem.security;

import com.nutriem.model.Patient;
import com.nutriem.repository.PatientRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientUserDetailsService implements UserDetailsService {

    private final PatientRepository patientRepository;

    public PatientUserDetailsService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // prefix "patient:" so JwtAuthFilter can route to correct service
    public static String toSubject(Long patientId) { return "patient:" + patientId; }
    public static boolean isPatientSubject(String s) { return s != null && s.startsWith("patient:"); }
    public static Long extractPatientId(String s) { return Long.parseLong(s.replace("patient:", "")); }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String subject) throws UsernameNotFoundException {
        Long id = extractPatientId(subject);
        Patient p = patientRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Patient not found: " + id));
        if (p.getPortalPassword() == null)
            throw new UsernameNotFoundException("Portal not enabled for patient: " + id);
        return new org.springframework.security.core.userdetails.User(
                subject,
                p.getPortalPassword(),
                true, true, true, true,
                List.of(new SimpleGrantedAuthority("ROLE_PATIENT"))
        );
    }
}
