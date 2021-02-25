package com.avec.api.health;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import com.avec.api.health.provider.PatientProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/*")
@Component
@ComponentScan(basePackages = {"com.avec.api.health"})
public class FhirRestfulServer extends RestfulServer {

    @Autowired
    PatientProvider patientProvider;

    public FhirRestfulServer() {
//        super(FhirContext.forR4());
    }

    @Override
    protected void initialize() throws ServletException {
        List<IResourceProvider> providers = new ArrayList<IResourceProvider>();
        providers.add(patientProvider);
        setResourceProviders(providers);
    }
}
