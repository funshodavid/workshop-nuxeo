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

package com.workshop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.runtime.model.ComponentInstance;
import org.nuxeo.runtime.model.DefaultComponent;

import com.workshop.extension.OperationDescriptor;

public class WorkshopServiceImpl extends DefaultComponent implements WorkshopService {

    public static final String OPERATION_EP = "operations";

    protected static final Log log = LogFactory.getLog(WorkshopServiceImpl.class);

    protected OperationDescriptor desc = new OperationDescriptor();

    @Override
    public double computePrice(ProductAdapter product) {
        double newPrice = product.getPrice();
        double amount = desc.getAmount();
        switch (desc.getOperator()) {
        case "plus":
            newPrice += amount;
            log.info("Operator plus succeeded");
            break;
        case "minus":
            newPrice -= amount;
            log.info("Operator minus succeeded");
            break;
        case "multiply":
            newPrice *= amount;
            log.info("Operator multiply succeeded");
            break;
        case "divide":
            newPrice /= amount;
            log.info("Operator divide succeeded");
            break;
        default:
            log.error("Operator unknown");
            break;
        }
        return newPrice;
    }

    @Override
    public void registerContribution(Object contribution, String extensionPoint, ComponentInstance contributor) {
        if (OPERATION_EP.equals(extensionPoint)) {
            desc = (OperationDescriptor) contribution;
        } else {
            log.error("Unable to handle unknown extensionPoint " + extensionPoint);
        }
    }

    @Override
    public void unregisterContribution(Object contribution, String extensionPoint, ComponentInstance contributor) {
        desc.setAmount(1.0);
        desc.setOperator("plus");
    }

    public String getOperator() {
        return desc.getOperator();
    }

    public String getAmount() {
        return String.valueOf(desc.getAmount());
    }

}
