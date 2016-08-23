package com.workshop.operations;

import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.collectors.DocumentModelCollector;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.runtime.api.Framework;

import com.workshop.ProductAdapter;
import com.workshop.WorkshopService;

/**
 *
 */
@Operation(id = PriceUpdater.ID, category = Constants.CAT_DOCUMENT, label = "Price updater", description = "Operation to update the price of a product")
public class PriceUpdater {

    public static final String ID = "Document.PriceUpdater";

    static final String PRODUCT_TYPE = "Product";

    static final String PRODUCT_SCHEMA = "product";

    @Context
    protected CoreSession session;

    @OperationMethod(collector = DocumentModelCollector.class)
    public DocumentModel run(DocumentModel doc) throws NuxeoException {

        if (!(PRODUCT_TYPE.equals(doc.getType()))) {
            throw new NuxeoException("Operation works only with " + PRODUCT_TYPE + " document type.");
        }

        ProductAdapter product = doc.getAdapter(ProductAdapter.class);
        WorkshopService service = Framework.getService(WorkshopService.class);

        double initialPrice = service.computePrice(product);

        product.setPrice(initialPrice);
        product.save();

        return doc;
    }
}
