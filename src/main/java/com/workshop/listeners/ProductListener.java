/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Funsho David
 */

package com.workshop.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;

import com.workshop.ProductAdapter;

public class ProductListener implements EventListener {

    protected static final Log log = LogFactory.getLog(ProductListener.class);

    @Override
    public void handleEvent(Event event) {
        EventContext ctx = event.getContext();
        if (!(ctx instanceof DocumentEventContext)) {
            return;
        }

        DocumentEventContext docCtx = (DocumentEventContext) ctx;
        CoreSession session = ctx.getCoreSession();
        DocumentModel doc = docCtx.getSourceDocument();

        if (doc == null) {
            return;
        }
        String type = doc.getType();
        if ("Product".equals(type)) {
            process(doc, session);
        }
    }

    private void process(DocumentModel doc, CoreSession session) {

        ProductAdapter product = doc.getAdapter(ProductAdapter.class);

        if (!product.getAvailability()) {

            PathRef pathRef = new PathRef("/default-domain/workspaces/ProductRepository/HiddenFolder");
            session.move(doc.getRef(), pathRef, null);
        }

    }
}
