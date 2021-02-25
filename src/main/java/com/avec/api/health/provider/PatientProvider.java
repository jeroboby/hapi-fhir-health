package com.avec.api.health.provider;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import com.avec.api.health.service.PatientService;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ComponentScan(basePackages = {"com.avec.api.health"})
@Controller
public class PatientProvider implements IResourceProvider {

    public Map<String, Patient> patientsMap = new HashMap<String, Patient>();

    @Autowired
    PatientService patientService;

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Patient.class;
    }

    /**
     * @param theParam
     * @return a list of patients
     */
    @Search
    public Bundle search(@RequiredParam(name = Patient.SP_FAMILY) StringParam theParam) {

        return patientService.searchPatient(theParam.toString());
    }

    /**
     * @param theId
     * @return a list of patients
     * This method will support a query like this http://localhost:8080/Patient/1
     */
    @Read
    public Patient read(@IdParam IdType theId) {
        createFakePatients();
        Patient retVal = patientsMap.get(theId.getIdPart());
        if (retVal == null) {
            throw new ResourceNotFoundException(theId);
        }
        return retVal;
    }


    public List<Patient> searchByFamilyName(String familyName) {
        List<Patient> retPatients;
        createFakePatients();
        retPatients = patientsMap.values()
                .stream()
                .filter(nextPatient ->
                        familyName.toLowerCase().equals(nextPatient.getNameFirstRep().getFamily().toLowerCase()))
                .collect(Collectors.toList());
        return retPatients;
    }

    public void createFakePatients() {
        // Create 9 patients
        for (int i = 1; i < 10; i++) {
            Patient patient = new Patient();
            patient = new Patient();
            patient.setId(Integer.toString(i));
            patient.addIdentifier()
                    .setSystem("http://acme.org/mrns")
                    .setValue("12543" + i);
            patient.addName()
                    .setFamily("Abdel")
                    .addGiven("A")
                    .addGiven("Denis");
            patient.setGender(Enumerations.AdministrativeGender.MALE);
            patientsMap.put(Integer.toString(i), patient);
        }
    }


}
