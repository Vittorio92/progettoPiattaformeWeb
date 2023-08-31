import { Prodotto } from "./prodotto.models";

export class ProdottoInCarrello{
    id!: number;
    qnt!: number;
    prezzo!: number;
    prodotto!: Prodotto;
}