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

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.events.EventStoreProvider;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserModel.RequiredAction;
import org.keycloak.services.ServicesLogger;

/**
 * @author <a href="mailto:ricardoabreum@gmail.com">Ricardo Abreu Medeiros</a>
 * @version $Revision: 1 $
 */
public class ReviewProfileFirstLogin implements Authenticator {
	
    protected static ServicesLogger log = ServicesLogger.LOGGER;

    private void enableUpdateProfile(AuthenticationFlowContext context) {
        if (!isLoginInClientOnce(context)) {
             context.getUser().addRequiredAction(RequiredAction.UPDATE_PROFILE); 
        }
    }

	private boolean isLoginInClientOnce(AuthenticationFlowContext context) {
		EventStoreProvider eventStore = context.getSession().getProvider(EventStoreProvider.class);
		return context.getRealm().isEventsEnabled() && !eventStore.createQuery()
    			.client(context.getAuthenticationSession().getClient().getClientId()) //clientId
    			.realm(context.getRealm().getId()) //realm
    			.user(context.getUser().getId())	//user
    			.type(EventType.LOGIN)				//EventType
    			.firstResult(0).maxResults(1).getResultList().isEmpty();
	}

    @Override
    public void authenticate(AuthenticationFlowContext context) {
    	enableUpdateProfile(context);
        context.success();
    }
    
    @Override
    public void action(AuthenticationFlowContext context) {
    	// never called
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
    	// never called
       return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
       // never called
    }

    @Override
    public void close() {

    }

    
}