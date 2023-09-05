import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { KeycloakService, KeycloakAngularModule } from 'keycloak-angular';
import { OAuthModule } from 'angular-oauth2-oidc';
import { CarrelloComponent } from './components/carrello/carrello.component';
import { HttpClientModule } from '@angular/common/http';
import { BarraRicercaComponent } from './components/barra-ricerca/barra-ricerca.component';
import { AcquistiComponent } from './components/acquisti/acquisti.component';
import { NegozioProdottiComponent } from './components/negozio-prodotti/negozio-prodotti.component';
import { AggiungiProdottoComponent } from './components/aggiungi-prodotto/aggiungi-prodotto.component';
import { AdminPageComponent } from './components/admin-page/admin-page.component';
import { SupportComponent } from './components/support/support.component';
import { ProfiloComponent } from './components/profilo/profilo.component';
import {MatCardModule} from '@angular/material/card';
import {MatSelectModule} from '@angular/material/select';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';


function initializeKeycloak(keycloak: KeycloakService):()=> Promise<boolean> {
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

@NgModule({
  declarations: [
    AppComponent,
    CarrelloComponent,
    BarraRicercaComponent,
    AcquistiComponent,
    NegozioProdottiComponent,
    AggiungiProdottoComponent,
    AdminPageComponent,
    SupportComponent,
    ProfiloComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    OAuthModule.forRoot({
      resourceServer: {
          allowedUrls: ['http://localhost:8081/'],
          sendAccessToken: true
      }
  }),
  KeycloakAngularModule,
  HttpClientModule,
  FormsModule,
  MatCardModule,
  MatSelectModule,
  BrowserAnimationsModule,
  MatButtonModule
  ],
  providers: [ {
    provide: APP_INITIALIZER,
    useFactory: initializeKeycloak,
    multi: true,
    deps: [KeycloakService],
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
