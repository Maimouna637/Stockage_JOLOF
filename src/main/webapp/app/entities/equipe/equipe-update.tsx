import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './equipe.reducer';

export const EquipeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const equipeEntity = useAppSelector(state => state.equipe.entity);
  const loading = useAppSelector(state => state.equipe.loading);
  const updating = useAppSelector(state => state.equipe.updating);
  const updateSuccess = useAppSelector(state => state.equipe.updateSuccess);

  const handleClose = () => {
    navigate('/equipe');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
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

    const entity = {
      ...equipeEntity,
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
          ...equipeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="stockageApp.equipe.home.createOrEditLabel" data-cy="EquipeCreateUpdateHeading">
            <Translate contentKey="stockageApp.equipe.home.createOrEditLabel">Create or edit a Equipe</Translate>
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
                  id="equipe-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              )}
              <ValidatedField label={translate('stockageApp.equipe.nom')} id="equipe-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField
                label={translate('stockageApp.equipe.prenom')}
                id="equipe-prenom"
                name="prenom"
                data-cy="prenom"
                type="text"
              />
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/equipe" replace variant="info">
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

export default EquipeUpdate;
