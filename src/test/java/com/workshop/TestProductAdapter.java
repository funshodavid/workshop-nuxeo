package com.workshop;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy({ "com.workshop.workshop-core", "studio.extensions.fdavid-SANDBOX" })
public class TestProductAdapter {
    @Inject
    CoreSession session;

    @Test
    public void shouldCallTheAdapter() {
        String doctype = "Product";
        String testTitle = "My Adapter Title";

        DocumentModel doc = session.createDocumentModel("/", "workshop-adapter", doctype);
        ProductAdapter adapter = doc.getAdapter(ProductAdapter.class);
        adapter.setTitle(testTitle);
        adapter.create();
        // session.save() is only needed in the context of unit tests
        session.save();

        Assert.assertNotNull("The adapter can't be used on the " + doctype + " document type", adapter);
        Assert.assertEquals("Document title does not match when using the adapter", testTitle, adapter.getTitle());
    }
}
