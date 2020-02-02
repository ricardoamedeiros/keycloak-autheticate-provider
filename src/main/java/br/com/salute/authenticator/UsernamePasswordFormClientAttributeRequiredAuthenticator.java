/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.salute.authenticator;

import java.util.Optional;

import javax.ws.rs.core.Response;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordForm;
import org.keycloak.models.RoleModel;
import org.keycloak.services.ServicesLogger;

/**
 * @author <a href="mailto:ricardoabreum@gmail.com">Ricardo Abreu Medeiros</a>
 * @version $Revision: 1 $
 */
public class UsernamePasswordFormClientAttributeRequiredAuthenticator extends UsernamePasswordForm  {
    protected static ServicesLogger log = ServicesLogger.LOGGER;

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        Response challengeResponse = context.form().createLoginPassword();
        context.challenge(challengeResponse);
        enableUpdateProfile(context);
     
        
    }

    private void enableUpdateProfile(AuthenticationFlowContext context) {
    	
    	Optional<RoleModel> optionalRole =  context.getAuthenticationSession().getClient().getRoles().stream().filter(e-> e.getName().equalsIgnoreCase("first_access")).findFirst();
    	if(optionalRole.isPresent()) {
    	 	RoleModel rm = optionalRole.get();
			/*
			 * if(!context.getUser().hasRole(rm)){ context.getUser().grantRole(rm);
			 * context.getUser().addRequiredAction(RequiredAction.UPDATE_PROFILE); }
			 */
    	}
    }
}