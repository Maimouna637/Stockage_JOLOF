import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './materiel-en-stock.reducer';

export const MaterielEnStockDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const materielEnStockEntity = useAppSelector(state => state.materielEnStock.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materielEnStockDetailsHeading">
          <Translate contentKey="stockageApp.materielEnStock.detail.title">MaterielEnStock</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{materielEnStockEntity.id}</dd>
          <dt>
            <span id="dateReception">
              <Translate contentKey="stockageApp.materielEnStock.dateReception">Date Reception</Translate>
            </span>
          </dt>
          <dd>
            {materielEnStockEntity.dateReception ? (
              <TextFormat value={materielEnStockEntity.dateReception} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="quantite">
              <Translate contentKey="stockageApp.materielEnStock.quantite">Quantite</Translate>
            </span>
          </dt>
          <dd>{materielEnStockEntity.quantite}</dd>
          <dt>
            <span id="materiel">
              <Translate contentKey="stockageApp.materielEnStock.materiel">Materiel</Translate>
            </span>
          </dt>
          <dd>{materielEnStockEntity.materiel}</dd>
          <dt>
            <span id="noserie">
              <Translate contentKey="stockageApp.materielEnStock.noserie">Noserie</Translate>
            </span>
          </dt>
          <dd>{materielEnStockEntity.noserie}</dd>
        </dl>
        <Button as={Link as any} to="/materiel-en-stock" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/materiel-en-stock/${materielEnStockEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MaterielEnStockDetail;
