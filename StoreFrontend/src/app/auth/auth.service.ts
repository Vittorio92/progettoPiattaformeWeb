import { Injectable } from '@angular/core';
import { OAuthService, AuthConfig, JwksValidationHandler } from 'angular-oauth2-oidc';
import jwtDecode from 'jwt-decode';
import { User } from './user';
import { jsonToken } from './jsonToken';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private oauthService: OAuthService) {
    this.configure();
  }

  authConfig: AuthConfig = {
    issuer: 'http://localhost:8080/realms/Store',
    redirectUri: window.location.origin,
    clientId: 'StoreCLient',
    scope: 'openid',
    responseType: 'code',
    // at_hash is not present in JWT token
    disableAtHashCheck: true,
    showDebugInformation: true
  }

  private configure() {
    this.oauthService.configure(this.authConfig);
    this.oauthService.tokenValidationHandler = new  JwksValidationHandler();
    this.oauthService.setupAutomaticSilentRefresh();
    this.oauthService.loadDiscoveryDocumentAndTryLogin();
  }

  public login(){
    this.oauthService.initLoginFlow();
  }

  public logoff() {
    this.oauthService.logOut();
  }

  public getInformation():string{
    return this.oauthService.getAccessToken();
  }

  public isauthenticated(){
    return this.oauthService.hasValidAccessToken();
  }

  public getEmail(){
    const currUser:User= <User> this.oauthService.getIdentityClaims();
    return currUser.preferred_username;
  }

  public getInfoAboutUser():jsonToken{
    const token=this.oauthService.getAccessToken();
    const decodedToken=<jsonToken>jwtDecode(token);
    return decodedToken;
  }

  public isAdmin(){
    const token=this.oauthService.getAccessToken();
    const decodedToken=<jsonToken>jwtDecode(token);
    return decodedToken.realm_access.roles.includes('admin');
  }

}
