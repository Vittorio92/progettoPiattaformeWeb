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
  utente!: any;
  verificato: boolean = false;

  barraRicerca_visibile: boolean = false
  carrello_visibile: boolean = false
  negozio_visibile = false;



  constructor(public auth: AuthService){}

  public isAuthenticated(): boolean {
    return this.auth.isauthenticated();
  }

  login() {
    if (this.auth.isauthenticated()) {
      alert("Hai già effettuato l'accesso come: " + this.auth.getNomeECognome);
    }
    this.auth.login();
  }

  logout(){
    this.auth.logout();
  }

  mostra_barraRicerca(): void {
    this.barraRicerca_visibile = true
    this.carrello_visibile = false
    this.negozio_visibile = false
  }

  mostra_carrello(): void {
    if (!this.isAuthenticated()) {
      alert("Effettuare il log-in per visualizzare il carrello")
    }
    else {
      this.barraRicerca_visibile = false
      this.carrello_visibile = true
      this.negozio_visibile = false
    }
  }

  mostra_prodotti(): void {
    this.barraRicerca_visibile = false
    this.negozio_visibile = true
    this.carrello_visibile = false
  }

}
