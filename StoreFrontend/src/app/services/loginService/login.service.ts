import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Utente } from 'src/app/models/utente.models';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private uri= "http://localhost:8081/login/logged"

  constructor(private http: HttpClient) { }

  public getOrAdd(email: string, nome: string, cognome: string){
    return this.http.get<Utente>(this.uri+"?email="+email+"&nome="+nome+"&cognome="+cognome);
  }
}
