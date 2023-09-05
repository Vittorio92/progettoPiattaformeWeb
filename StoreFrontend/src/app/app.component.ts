import { Component } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { KeycloakService } from 'keycloak-angular';
import { LoginService } from './services/loginService/login.service';
import { ThisReceiver } from '@angular/compiler';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'StoreFrontend';
  utente!: any;
  verificato: boolean = false;

  barraRicerca_visibile: boolean = false
  carrello_visibile: boolean = false
  negozio_visibile: boolean = false
  admin_visibile: boolean = false
  support_visibile: boolean = false
  profilo_visibile: boolean = false



  constructor(public auth: AuthService, private loginS: LoginService){}

  public isAuthenticated(): boolean {
    return this.auth.isauthenticated();
  }

  login() {
    if (this.auth.isauthenticated()) {
      alert("Hai già effettuato l'accesso come: " + this.auth.getNomeECognome);
    }else{
      this.auth.login();
    }
  }

  logout(){
    this.auth.logout();
  }

  mostra_barraRicerca(): void {
    this.loginS.getOrAdd(this.auth.getEmail(), this.auth.getNome(), this.auth.getCognome()).subscribe();
    this.barraRicerca_visibile = true;
    this.carrello_visibile = false;
    this.profilo_visibile = false;
    this.negozio_visibile = false;
    this.admin_visibile = false;
    this.support_visibile = false;
  }

  mostra_carrello(): void {
    if (!this.isAuthenticated()) {
      alert("Effettuare il log-in per visualizzare il carrello");
    }
    else {
      this.loginS.getOrAdd(this.auth.getEmail(), this.auth.getNome(), this.auth.getCognome()).subscribe();
      this.barraRicerca_visibile = false;
    this.carrello_visibile = true;
    this.profilo_visibile = false;
    this.negozio_visibile = false;
    this.admin_visibile = false;
    this.support_visibile = false;
    }
  }

  mostra_prodotti(): void {
    this.loginS.getOrAdd(this.auth.getEmail(), this.auth.getNome(), this.auth.getCognome()).subscribe();
    this.barraRicerca_visibile = false;
    this.carrello_visibile = false;
    this.profilo_visibile = false;
    this.negozio_visibile = true;
    this.admin_visibile = false;
    this.support_visibile = false;
  }

  mostra_support(): void{
    this.loginS.getOrAdd(this.auth.getEmail(), this.auth.getNome(), this.auth.getCognome()).subscribe();
    this.barraRicerca_visibile = false;
    this.carrello_visibile = false;
    this.profilo_visibile = false;
    this.negozio_visibile = false;
    this.admin_visibile = false;
    this.support_visibile = true;
  }

  mostra_admin(): void{
    this.loginS.getOrAdd(this.auth.getEmail(), this.auth.getNome(), this.auth.getCognome()).subscribe();
    this.barraRicerca_visibile = false;
    this.carrello_visibile = false;
    this.profilo_visibile = false;
    this.negozio_visibile = false;
    this.admin_visibile = true;
    this.support_visibile = false;
  }

  mostra_profilo(): void{
    this.loginS.getOrAdd(this.auth.getEmail(), this.auth.getNome(), this.auth.getCognome()).subscribe();
    this.barraRicerca_visibile = false;
    this.carrello_visibile = false;
    this.profilo_visibile = true;
    this.negozio_visibile = false;
    this.admin_visibile = false;
    this.support_visibile = false;
  }

}
