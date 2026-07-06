import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getEquipes } from 'app/entities/equipe/equipe.reducer';
import { Etat } from 'app/shared/model/enumerations/etat.model';
import { Models } from 'app/shared/model/enumerations/models.model';

import { createEntity, getEntity, updateEntity } from './materiel-sur-le-terrain.reducer';

export const MaterielSurLeTerrainUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const equipes = useAppSelector(state => state.equipe.entities);
  const materielSurLeTerrainEntity = useAppSelector(state => state.materielSurLeTerrain.entity);
  const loading = useAppSelector(state => state.materielSurLeTerrain.loading);
  const updating = useAppSelector(state => state.materielSurLeTerrain.updating);
  const updateSuccess = useAppSelector(state => state.materielSurLeTerrain.updateSuccess);
  const modelsValues = Object.keys(Models);
  const etatValues = Object.keys(Etat);

  const handleClose = () => {
    navigate('/materiel-sur-le-terrain');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getEquipes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.nombre1 !== undefined && typeof values.nombre1 !== 'number') {
      values.nombre1 = Number(values.nombre1);
    }
    if (values.nombre2 !== undefined && typeof values.nombre2 !== 'number') {
      values.nombre2 = Number(values.nombre2);
    }
    if (values.nombre3 !== undefined && typeof values.nombre3 !== 'number') {
      values.nombre3 = Number(values.nombre3);
    }
    if (values.nombre4 !== undefined && typeof values.nombre4 !== 'number') {
      values.nombre4 = Number(values.nombre4);
    }

    const entity = {
      ...materielSurLeTerrainEntity,
      ...values,
      equipe: equipes.find(it => it.id.toString() === values.equipe?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          materiel1: 'AI9',
          etat1: 'BON',
          materiel2: 'AI9',
          etat2: 'BON',
          materiel3: 'AI9',
          etat3: 'BON',
          materiel4: 'AI9',
          etat4: 'BON',
          ...materielSurLeTerrainEntity,
          equipe: materielSurLeTerrainEntity?.equipe?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="stockageApp.materielSurLeTerrain.home.createOrEditLabel" data-cy="MaterielSurLeTerrainCreateUpdateHeading">
            <Translate contentKey="stockageApp.materielSurLeTerrain.home.createOrEditLabel">
              Create or edit a MaterielSurLeTerrain
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="materiel-sur-le-terrain-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              )}
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.dateAffectattion')}
                id="materiel-sur-le-terrain-dateAffectattion"
                name="dateAffectattion"
                data-cy="dateAffectattion"
                type="date"
              />
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.materiel1')}
                id="materiel-sur-le-terrain-materiel1"
                name="materiel1"
                data-cy="materiel1"
                type="select"
              >
                {modelsValues.map(models => (
                  <option value={models} key={models}>
                    {translate(`stockageApp.Models.${models}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.serie1')}
                id="materiel-sur-le-terrain-serie1"
                name="serie1"
                data-cy="serie1"
                type="text"
              />
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.etat1')}
                id="materiel-sur-le-terrain-etat1"
                name="etat1"
                data-cy="etat1"
                type="select"
              >
                {etatValues.map(etat => (
                  <option value={etat} key={etat}>
                    {translate(`stockageApp.Etat.${etat}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.nombre1')}
                id="materiel-sur-le-terrain-nombre1"
                name="nombre1"
                data-cy="nombre1"
                type="text"
              />
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.materiel2')}
                id="materiel-sur-le-terrain-materiel2"
                name="materiel2"
                data-cy="materiel2"
                type="select"
              >
                {modelsValues.map(models => (
                  <option value={models} key={models}>
                    {translate(`stockageApp.Models.${models}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.serie2')}
                id="materiel-sur-le-terrain-serie2"
                name="serie2"
                data-cy="serie2"
                type="text"
              />
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.etat2')}
                id="materiel-sur-le-terrain-etat2"
                name="etat2"
                data-cy="etat2"
                type="select"
              >
                {etatValues.map(etat => (
                  <option value={etat} key={etat}>
                    {translate(`stockageApp.Etat.${etat}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.nombre2')}
                id="materiel-sur-le-terrain-nombre2"
                name="nombre2"
                data-cy="nombre2"
                type="text"
              />
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.materiel3')}
                id="materiel-sur-le-terrain-materiel3"
                name="materiel3"
                data-cy="materiel3"
                type="select"
              >
                {modelsValues.map(models => (
                  <option value={models} key={models}>
                    {translate(`stockageApp.Models.${models}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.serie3')}
                id="materiel-sur-le-terrain-serie3"
                name="serie3"
                data-cy="serie3"
                type="text"
              />
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.etat3')}
                id="materiel-sur-le-terrain-etat3"
                name="etat3"
                data-cy="etat3"
                type="select"
              >
                {etatValues.map(etat => (
                  <option value={etat} key={etat}>
                    {translate(`stockageApp.Etat.${etat}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.nombre3')}
                id="materiel-sur-le-terrain-nombre3"
                name="nombre3"
                data-cy="nombre3"
                type="text"
              />
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.materiel4')}
                id="materiel-sur-le-terrain-materiel4"
                name="materiel4"
                data-cy="materiel4"
                type="select"
              >
                {modelsValues.map(models => (
                  <option value={models} key={models}>
                    {translate(`stockageApp.Models.${models}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.serie4')}
                id="materiel-sur-le-terrain-serie4"
                name="serie4"
                data-cy="serie4"
                type="text"
              />
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.etat4')}
                id="materiel-sur-le-terrain-etat4"
                name="etat4"
                data-cy="etat4"
                type="select"
              >
                {etatValues.map(etat => (
                  <option value={etat} key={etat}>
                    {translate(`stockageApp.Etat.${etat}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('stockageApp.materielSurLeTerrain.nombre4')}
                id="materiel-sur-le-terrain-nombre4"
                name="nombre4"
                data-cy="nombre4"
                type="text"
              />
              <ValidatedField
                id="materiel-sur-le-terrain-equipe"
                name="equipe"
                data-cy="equipe"
                label={translate('stockageApp.materielSurLeTerrain.equipe')}
                type="select"
              >
                <option value="" key="0" />
                {equipes
                  ? equipes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                as={Link as any}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/materiel-sur-le-terrain"
                replace
                variant="info"
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button variant="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MaterielSurLeTerrainUpdate;
