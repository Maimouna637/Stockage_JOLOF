export interface IEquipe {
  id?: number;
  nom?: string | null;
  prenom?: string | null;
}

export const defaultValue: Readonly<IEquipe> = {};
