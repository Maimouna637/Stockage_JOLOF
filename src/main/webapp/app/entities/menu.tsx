import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/equipe">
        <Translate contentKey="global.menu.entities.equipe" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/materiel-sur-le-terrain">
        <Translate contentKey="global.menu.entities.materielSurLeTerrain" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/materiel-en-stock">
        <Translate contentKey="global.menu.entities.materielEnStock" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
