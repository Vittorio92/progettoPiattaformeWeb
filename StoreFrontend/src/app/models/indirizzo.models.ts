import { Utente } from "./utente.models";

export class Indirizzo{
    id!: number;
    citta!: string;
    via!: string;
    numeroCivico!: number;
    cap!: number;
    utente! : Utente;
}