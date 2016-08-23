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

package com.workshop.security;

import java.security.Principal;

import org.nuxeo.ecm.core.api.security.ACP;
import org.nuxeo.ecm.core.api.security.Access;
import org.nuxeo.ecm.core.model.Document;
import org.nuxeo.ecm.core.security.AbstractSecurityPolicy;
import org.nuxeo.ecm.core.security.SecurityPolicy;
import org.nuxeo.ecm.platform.usermanager.UserManager;
import org.nuxeo.runtime.api.Framework;

public class WorkshopSecurityPolicy extends AbstractSecurityPolicy implements SecurityPolicy {

    @Override
    public Access checkPermission(Document document, ACP acp, Principal principal, String s, String[] strings,
            String[] strings1) {

        UserManager userManager = Framework.getService(UserManager.class);

        if ("/default-domain/workspaces/ProductRepository/HiddenFolder".equals(document.getPath())
                && userManager.getGroup("utilisateurs").getMemberUsers().contains(principal.getName())) {
            return Access.DENY;
        }
        return Access.GRANT;
    }

}
