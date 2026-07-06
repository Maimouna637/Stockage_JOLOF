import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MaterielEnStock from './materiel-en-stock';
import MaterielEnStockDeleteDialog from './materiel-en-stock-delete-dialog';
import MaterielEnStockDetail from './materiel-en-stock-detail';
import MaterielEnStockUpdate from './materiel-en-stock-update';

const MaterielEnStockRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MaterielEnStock />} />
    <Route path="new" element={<MaterielEnStockUpdate />} />
    <Route path=":id">
      <Route index element={<MaterielEnStockDetail />} />
      <Route path="edit" element={<MaterielEnStockUpdate />} />
      <Route path="delete" element={<MaterielEnStockDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MaterielEnStockRoutes;
