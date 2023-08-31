import { KeycloakService } from "keycloak-angular";

export function initializeKeycloak(keycloak: KeycloakService):()=> Promise<boolean> {
    return () =>
        keycloak.init({
        config: {
        url: 'http://localhost:8080',
        realm: 'Store',
        clientId: 'StoreClient'
        },
        initOptions:{
            checkLoginIframe: true,
            checkLoginIframeInterval: 25
        }
    });
}