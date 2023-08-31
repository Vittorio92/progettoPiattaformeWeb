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

  constructor(private auth: KeycloakService){}

  login(){
    this.auth.login();
  }
}
