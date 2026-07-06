import React, { useEffect, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { Translate, getSortState } from 'react-jhipster';
import { Link, useLocation, useNavigate } from 'react-router';

import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC } from 'app/shared/util/pagination.constants';

import { getEntities } from './equipe.reducer';

export const Equipe = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const equipeList = useAppSelector(state => state.equipe.entities);
  const loading = useAppSelector(state => state.equipe.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="equipe-heading" data-cy="EquipeHeading">
        <Translate contentKey="stockageApp.equipe.home.title">Equipes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" variant="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="stockageApp.equipe.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/equipe/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="stockageApp.equipe.home.createLabel">Create new Equipe</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {equipeList?.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="stockageApp.equipe.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('nom')}>
                  <Translate contentKey="stockageApp.equipe.nom">Nom</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('nom')} />
                </th>
                <th className="hand" onClick={sort('prenom')}>
                  <Translate contentKey="stockageApp.equipe.prenom">Prenom</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('prenom')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {equipeList.map(equipe => (
                <tr key={`entity-${equipe.id}`} data-cy="entityTable">
                  <td>
                    <Button as={Link as any} to={`/equipe/${equipe.id}`} variant="link" size="sm">
                      {equipe.id}
                    </Button>
                  </td>
                  <td>{equipe.nom}</td>
                  <td>{equipe.prenom}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button as={Link as any} to={`/equipe/${equipe.id}`} variant="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button as={Link as any} to={`/equipe/${equipe.id}/edit`} variant="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/equipe/${equipe.id}/delete`)}
                        variant="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="stockageApp.equipe.home.notFound">No Equipes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Equipe;
