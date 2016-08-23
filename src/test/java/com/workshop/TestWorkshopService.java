package com.workshop;

import static org.junit.Assert.*;

import com.workshop.features.StudioFeature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.LocalDeploy;

import com.google.inject.Inject;

@RunWith(FeaturesRunner.class)
@Features({ AutomationFeature.class, StudioFeature.class })
@Deploy({ "com.workshop.workshop-core"})
@LocalDeploy("com.workshop.workshop-core:OSGI-INF/workshop-service-contrib.xml")
public class TestWorkshopService {

    protected static final Log log = LogFactory.getLog(TestWorkshopService.class);

    @Inject
    protected WorkshopService service;

    @Inject
    protected CoreSession coreSession;

    @Test
    public void isWorkshopServiceAvailable() {
        assertNotNull(service);
    }

    @Test
    public void testComputePrice() throws Exception {

        DocumentModel doc = coreSession.createDocumentModel("/", "myProduct", "Product");

        // DocumentModel hiddenDoc = coreSession.createDocumentModel("/", "hidden", "Folder");

        ProductAdapter product = doc.getAdapter(ProductAdapter.class);
        product.setAvailability(true);
        product.setTitle("Product Title");
        product.setPrice(10.0);

        doc = coreSession.createDocument(doc);
        // hiddenDoc = coreSession.createDocument(hiddenDoc);
        coreSession.save();

        IdRef idDocRef = new IdRef(doc.getId());
        doc = coreSession.getDocument(idDocRef);

        double newPrice = service.computePrice(product);
        assertEquals(8.0, newPrice, 0);

        doc.setPropertyValue("product:availability", false);
        coreSession.saveDocument(doc);

        //assertTrue(coreSession.exists(new PathRef("/hidden/myProduct")));
    }

}
