import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Translate } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './equipe.reducer';

export const EquipeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const equipeEntity = useAppSelector(state => state.equipe.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="equipeDetailsHeading">
          <Translate contentKey="stockageApp.equipe.detail.title">Equipe</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{equipeEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="stockageApp.equipe.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{equipeEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="stockageApp.equipe.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{equipeEntity.prenom}</dd>
        </dl>
        <Button as={Link as any} to="/equipe" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/equipe/${equipeEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EquipeDetail;
