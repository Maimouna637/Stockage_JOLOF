import dayjs from 'dayjs';

import { Etat } from 'app/shared/model/enumerations/etat.model';
import { Models } from 'app/shared/model/enumerations/models.model';
import { IEquipe } from 'app/shared/model/equipe.model';

export interface IMaterielSurLeTerrain {
  id?: number;
  dateAffectattion?: dayjs.Dayjs | null;
  materiel1?: keyof typeof Models | null;
  serie1?: string | null;
  etat1?: keyof typeof Etat | null;
  nombre1?: number | null;
  materiel2?: keyof typeof Models | null;
  serie2?: string | null;
  etat2?: keyof typeof Etat | null;
  nombre2?: number | null;
  materiel3?: keyof typeof Models | null;
  serie3?: string | null;
  etat3?: keyof typeof Etat | null;
  nombre3?: number | null;
  materiel4?: keyof typeof Models | null;
  serie4?: string | null;
  etat4?: keyof typeof Etat | null;
  nombre4?: number | null;
  equipe?: IEquipe | null;
}

export const defaultValue: Readonly<IMaterielSurLeTerrain> = {};
