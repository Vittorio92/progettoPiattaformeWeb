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
  negozio_visibile: boolean = true
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
    if(this.auth.isauthenticated()){
      if (!this.verificato){
        this.loginS.getOrAdd(this.auth.getEmail(), this.auth.getNome(), this.auth.getCognome()).subscribe();
        this.verificato = true;
      }
    }
    
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
      if(this.auth.isauthenticated()){
        if (!this.verificato){
          this.loginS.getOrAdd(this.auth.getEmail(), this.auth.getNome(), this.auth.getCognome()).subscribe();
          this.verificato = true;
        }
      }
      this.barraRicerca_visibile = false;
      this.carrello_visibile = true;
      this.profilo_visibile = false;
      this.negozio_visibile = false;
      this.admin_visibile = false;
      this.support_visibile = false;
    }
  }

  mostra_prodotti(): void {
    if(this.auth.isauthenticated()){
      if (!this.verificato){
        this.loginS.getOrAdd(this.auth.getEmail(), this.auth.getNome(), this.auth.getCognome()).subscribe();
        this.verificato = true;
      }
    }

    this.barraRicerca_visibile = false;
    this.carrello_visibile = false;
    this.profilo_visibile = false;
    this.negozio_visibile = true;
    this.admin_visibile = false;
    this.support_visibile = false;
  }

  mostra_support(): void{
    if(this.auth.isauthenticated()){
      if (!this.verificato){
        this.loginS.getOrAdd(this.auth.getEmail(), this.auth.getNome(), this.auth.getCognome()).subscribe();
        this.verificato = true;
      }
    }

    this.barraRicerca_visibile = false;
    this.carrello_visibile = false;
    this.profilo_visibile = false;
    this.negozio_visibile = false;
    this.admin_visibile = false;
    this.support_visibile = true;
  }

  mostra_admin(): void{
    this.barraRicerca_visibile = false;
    this.carrello_visibile = false;
    this.profilo_visibile = false;
    this.negozio_visibile = false;
    this.admin_visibile = true;
    this.support_visibile = false;
  }

  mostra_profilo(): void{
    if(this.auth.isauthenticated()){
      if (!this.verificato){
        this.loginS.getOrAdd(this.auth.getEmail(), this.auth.getNome(), this.auth.getCognome()).subscribe();
        this.verificato = true;
      }
    }

    this.barraRicerca_visibile = false;
    this.carrello_visibile = false;
    this.profilo_visibile = true;
    this.negozio_visibile = false;
    this.admin_visibile = false;
    this.support_visibile = false;
  }

}
