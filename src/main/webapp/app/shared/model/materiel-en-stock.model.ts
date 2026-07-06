import dayjs from 'dayjs';

import { Models } from 'app/shared/model/enumerations/models.model';

export interface IMaterielEnStock {
  id?: number;
  dateReception?: dayjs.Dayjs | null;
  quantite?: number | null;
  materiel?: keyof typeof Models | null;
  noserie?: string | null;
}

export const defaultValue: Readonly<IMaterielEnStock> = {};
