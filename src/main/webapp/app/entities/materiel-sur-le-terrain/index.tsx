import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MaterielSurLeTerrain from './materiel-sur-le-terrain';
import MaterielSurLeTerrainDeleteDialog from './materiel-sur-le-terrain-delete-dialog';
import MaterielSurLeTerrainDetail from './materiel-sur-le-terrain-detail';
import MaterielSurLeTerrainUpdate from './materiel-sur-le-terrain-update';

const MaterielSurLeTerrainRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MaterielSurLeTerrain />} />
    <Route path="new" element={<MaterielSurLeTerrainUpdate />} />
    <Route path=":id">
      <Route index element={<MaterielSurLeTerrainDetail />} />
      <Route path="edit" element={<MaterielSurLeTerrainUpdate />} />
      <Route path="delete" element={<MaterielSurLeTerrainDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MaterielSurLeTerrainRoutes;
