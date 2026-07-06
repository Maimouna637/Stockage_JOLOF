import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Equipe from './equipe';
import MaterielEnStock from './materiel-en-stock';
import MaterielSurLeTerrain from './materiel-sur-le-terrain';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="/equipe/*" element={<Equipe />} />
        <Route path="/materiel-sur-le-terrain/*" element={<MaterielSurLeTerrain />} />
        <Route path="/materiel-en-stock/*" element={<MaterielEnStock />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
