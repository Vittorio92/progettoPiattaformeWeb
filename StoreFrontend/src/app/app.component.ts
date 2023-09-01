import { Component } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'StoreFrontend';
  autenticato: boolean = this.auth.isauthenticated();

  constructor(private auth: AuthService){}

  login(){
    this.auth.login();
  }

  nome(){
    console.log(this.auth.getInfoAboutUser());
    console.log(this.auth.getEmail());
    console.log(this.auth.isauthenticated());
  }

  logout(){
    this.auth.logoff();
  }
}
