import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Equipe from './equipe';
import EquipeDeleteDialog from './equipe-delete-dialog';
import EquipeDetail from './equipe-detail';
import EquipeUpdate from './equipe-update';

const EquipeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Equipe />} />
    <Route path="new" element={<EquipeUpdate />} />
    <Route path=":id">
      <Route index element={<EquipeDetail />} />
      <Route path="edit" element={<EquipeUpdate />} />
      <Route path="delete" element={<EquipeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EquipeRoutes;
