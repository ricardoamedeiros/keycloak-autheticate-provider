Check for review profile in first login by client and realm
===================================================

1. First, Keycloak must be running.

2. Execute the follow.  This will build the example and deploy it

   $ mvn clean install wildfly:deploy

3. Login to admin console.  Hit browser refresh if you are already logged in so that the new providers show up.

4. Go to the Authentication menu item and go to the Flow tab, you will be able to view the currently
   defined flows.  You cannot modify an built in flows, so, to add the Authenticator you
   have to copy an existing flow or create your own.  Copy the "Browser" flow.

6. In your copy, click the "Actions" menu item and "Add Execution".  Pick "Review profile in first login by client"


