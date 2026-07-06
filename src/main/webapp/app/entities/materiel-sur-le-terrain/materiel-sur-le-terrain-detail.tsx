import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './materiel-sur-le-terrain.reducer';

export const MaterielSurLeTerrainDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const materielSurLeTerrainEntity = useAppSelector(state => state.materielSurLeTerrain.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materielSurLeTerrainDetailsHeading">
          <Translate contentKey="stockageApp.materielSurLeTerrain.detail.title">MaterielSurLeTerrain</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.id}</dd>
          <dt>
            <span id="dateAffectattion">
              <Translate contentKey="stockageApp.materielSurLeTerrain.dateAffectattion">Date Affectattion</Translate>
            </span>
          </dt>
          <dd>
            {materielSurLeTerrainEntity.dateAffectattion ? (
              <TextFormat value={materielSurLeTerrainEntity.dateAffectattion} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="materiel1">
              <Translate contentKey="stockageApp.materielSurLeTerrain.materiel1">Materiel 1</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.materiel1}</dd>
          <dt>
            <span id="serie1">
              <Translate contentKey="stockageApp.materielSurLeTerrain.serie1">Serie 1</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.serie1}</dd>
          <dt>
            <span id="etat1">
              <Translate contentKey="stockageApp.materielSurLeTerrain.etat1">Etat 1</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.etat1}</dd>
          <dt>
            <span id="nombre1">
              <Translate contentKey="stockageApp.materielSurLeTerrain.nombre1">Nombre 1</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.nombre1}</dd>
          <dt>
            <span id="materiel2">
              <Translate contentKey="stockageApp.materielSurLeTerrain.materiel2">Materiel 2</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.materiel2}</dd>
          <dt>
            <span id="serie2">
              <Translate contentKey="stockageApp.materielSurLeTerrain.serie2">Serie 2</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.serie2}</dd>
          <dt>
            <span id="etat2">
              <Translate contentKey="stockageApp.materielSurLeTerrain.etat2">Etat 2</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.etat2}</dd>
          <dt>
            <span id="nombre2">
              <Translate contentKey="stockageApp.materielSurLeTerrain.nombre2">Nombre 2</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.nombre2}</dd>
          <dt>
            <span id="materiel3">
              <Translate contentKey="stockageApp.materielSurLeTerrain.materiel3">Materiel 3</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.materiel3}</dd>
          <dt>
            <span id="serie3">
              <Translate contentKey="stockageApp.materielSurLeTerrain.serie3">Serie 3</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.serie3}</dd>
          <dt>
            <span id="etat3">
              <Translate contentKey="stockageApp.materielSurLeTerrain.etat3">Etat 3</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.etat3}</dd>
          <dt>
            <span id="nombre3">
              <Translate contentKey="stockageApp.materielSurLeTerrain.nombre3">Nombre 3</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.nombre3}</dd>
          <dt>
            <span id="materiel4">
              <Translate contentKey="stockageApp.materielSurLeTerrain.materiel4">Materiel 4</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.materiel4}</dd>
          <dt>
            <span id="serie4">
              <Translate contentKey="stockageApp.materielSurLeTerrain.serie4">Serie 4</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.serie4}</dd>
          <dt>
            <span id="etat4">
              <Translate contentKey="stockageApp.materielSurLeTerrain.etat4">Etat 4</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.etat4}</dd>
          <dt>
            <span id="nombre4">
              <Translate contentKey="stockageApp.materielSurLeTerrain.nombre4">Nombre 4</Translate>
            </span>
          </dt>
          <dd>{materielSurLeTerrainEntity.nombre4}</dd>
          <dt>
            <Translate contentKey="stockageApp.materielSurLeTerrain.equipe">Equipe</Translate>
          </dt>
          <dd>{materielSurLeTerrainEntity.equipe ? materielSurLeTerrainEntity.equipe.id : ''}</dd>
        </dl>
        <Button as={Link as any} to="/materiel-sur-le-terrain" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/materiel-sur-le-terrain/${materielSurLeTerrainEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MaterielSurLeTerrainDetail;
