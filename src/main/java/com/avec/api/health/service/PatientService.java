package com.avec.api.health.service;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.avec.api.health.FhirRestfulServer;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@ComponentScan(basePackages = {"com.avec.api.health"})
@Service
public class PatientService {

    @Autowired
    FhirRestfulServer fhirRestfulServer;

    @Value("${serverBase}")
    private String serverBase;

    public Bundle searchPatient(String name) {
        IGenericClient client = fhirRestfulServer.getFhirContext().newRestfulGenericClient(serverBase);
        List<Patient> patients = new ArrayList<>();
        Bundle result = client
                .search()
                .forResource(Patient.class)
                .where(Patient.FAMILY.matches().value(name))
                .returnBundle(Bundle.class)
                .execute();
        fhirRestfulServer.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(result);
        return result;
    }
}
