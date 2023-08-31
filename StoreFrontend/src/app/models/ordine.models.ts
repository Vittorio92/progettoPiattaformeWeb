import { Utente } from "./utente.models";
import { ProdottoInCarrello } from "./prodottoInCarrello.models";

export class Ordine{
    id!: number;
    data!: Date;
    acquirente!: Utente;
    carrello!: ProdottoInCarrello[];
}