import equipe from 'app/entities/equipe/equipe.reducer';
import materielEnStock from 'app/entities/materiel-en-stock/materiel-en-stock.reducer';
import materielSurLeTerrain from 'app/entities/materiel-sur-le-terrain/materiel-sur-le-terrain.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  equipe,
  materielSurLeTerrain,
  materielEnStock,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
