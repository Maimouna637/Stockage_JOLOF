import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Models } from 'app/shared/model/enumerations/models.model';

import { createEntity, getEntity, updateEntity } from './materiel-en-stock.reducer';

export const MaterielEnStockUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const materielEnStockEntity = useAppSelector(state => state.materielEnStock.entity);
  const loading = useAppSelector(state => state.materielEnStock.loading);
  const updating = useAppSelector(state => state.materielEnStock.updating);
  const updateSuccess = useAppSelector(state => state.materielEnStock.updateSuccess);
  const modelsValues = Object.keys(Models);

  const handleClose = () => {
    navigate('/materiel-en-stock');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
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
    if (values.quantite !== undefined && typeof values.quantite !== 'number') {
      values.quantite = Number(values.quantite);
    }

    const entity = {
      ...materielEnStockEntity,
      ...values,
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
          materiel: 'AI9',
          ...materielEnStockEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="stockageApp.materielEnStock.home.createOrEditLabel" data-cy="MaterielEnStockCreateUpdateHeading">
            <Translate contentKey="stockageApp.materielEnStock.home.createOrEditLabel">Create or edit a MaterielEnStock</Translate>
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
                  id="materiel-en-stock-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              )}
              <ValidatedField
                label={translate('stockageApp.materielEnStock.dateReception')}
                id="materiel-en-stock-dateReception"
                name="dateReception"
                data-cy="dateReception"
                type="date"
              />
              <ValidatedField
                label={translate('stockageApp.materielEnStock.quantite')}
                id="materiel-en-stock-quantite"
                name="quantite"
                data-cy="quantite"
                type="text"
              />
              <ValidatedField
                label={translate('stockageApp.materielEnStock.materiel')}
                id="materiel-en-stock-materiel"
                name="materiel"
                data-cy="materiel"
                type="select"
              >
                {modelsValues.map(models => (
                  <option value={models} key={models}>
                    {translate(`stockageApp.Models.${models}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('stockageApp.materielEnStock.noserie')}
                id="materiel-en-stock-noserie"
                name="noserie"
                data-cy="noserie"
                type="text"
              />
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/materiel-en-stock" replace variant="info">
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

export default MaterielEnStockUpdate;
