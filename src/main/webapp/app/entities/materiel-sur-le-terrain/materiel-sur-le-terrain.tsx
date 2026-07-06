import React, { useEffect, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { Link, useLocation } from 'react-router';

import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import InfiniteScroll from 'react-infinite-scroll-component';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

import { getEntities, reset } from './materiel-sur-le-terrain.reducer';

export const MaterielSurLeTerrain = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const materielSurLeTerrainList = useAppSelector(state => state.materielSurLeTerrain.entities);
  const loading = useAppSelector(state => state.materielSurLeTerrain.loading);
  const links = useAppSelector(state => state.materielSurLeTerrain.links);
  const updateSuccess = useAppSelector(state => state.materielSurLeTerrain.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((globalThis as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="materiel-sur-le-terrain-heading" data-cy="MaterielSurLeTerrainHeading">
        <Translate contentKey="stockageApp.materielSurLeTerrain.home.title">Materiel Sur Le Terrains</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" variant="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="stockageApp.materielSurLeTerrain.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/materiel-sur-le-terrain/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="stockageApp.materielSurLeTerrain.home.createLabel">Create new Materiel Sur Le Terrain</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={materielSurLeTerrainList ? materielSurLeTerrainList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {materielSurLeTerrainList?.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('dateAffectattion')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.dateAffectattion">Date Affectattion</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('dateAffectattion')} />
                  </th>
                  <th className="hand" onClick={sort('materiel1')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.materiel1">Materiel 1</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('materiel1')} />
                  </th>
                  <th className="hand" onClick={sort('serie1')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.serie1">Serie 1</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('serie1')} />
                  </th>
                  <th className="hand" onClick={sort('etat1')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.etat1">Etat 1</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('etat1')} />
                  </th>
                  <th className="hand" onClick={sort('nombre1')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.nombre1">Nombre 1</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('nombre1')} />
                  </th>
                  <th className="hand" onClick={sort('materiel2')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.materiel2">Materiel 2</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('materiel2')} />
                  </th>
                  <th className="hand" onClick={sort('serie2')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.serie2">Serie 2</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('serie2')} />
                  </th>
                  <th className="hand" onClick={sort('etat2')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.etat2">Etat 2</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('etat2')} />
                  </th>
                  <th className="hand" onClick={sort('nombre2')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.nombre2">Nombre 2</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('nombre2')} />
                  </th>
                  <th className="hand" onClick={sort('materiel3')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.materiel3">Materiel 3</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('materiel3')} />
                  </th>
                  <th className="hand" onClick={sort('serie3')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.serie3">Serie 3</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('serie3')} />
                  </th>
                  <th className="hand" onClick={sort('etat3')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.etat3">Etat 3</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('etat3')} />
                  </th>
                  <th className="hand" onClick={sort('nombre3')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.nombre3">Nombre 3</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('nombre3')} />
                  </th>
                  <th className="hand" onClick={sort('materiel4')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.materiel4">Materiel 4</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('materiel4')} />
                  </th>
                  <th className="hand" onClick={sort('serie4')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.serie4">Serie 4</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('serie4')} />
                  </th>
                  <th className="hand" onClick={sort('etat4')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.etat4">Etat 4</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('etat4')} />
                  </th>
                  <th className="hand" onClick={sort('nombre4')}>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.nombre4">Nombre 4</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('nombre4')} />
                  </th>
                  <th>
                    <Translate contentKey="stockageApp.materielSurLeTerrain.equipe">Equipe</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {materielSurLeTerrainList.map(materielSurLeTerrain => (
                  <tr key={`entity-${materielSurLeTerrain.id}`} data-cy="entityTable">
                    <td>
                      <Button as={Link as any} to={`/materiel-sur-le-terrain/${materielSurLeTerrain.id}`} variant="link" size="sm">
                        {materielSurLeTerrain.id}
                      </Button>
                    </td>
                    <td>
                      {materielSurLeTerrain.dateAffectattion ? (
                        <TextFormat type="date" value={materielSurLeTerrain.dateAffectattion} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      <Translate contentKey={`stockageApp.Models.${materielSurLeTerrain.materiel1}`} />
                    </td>
                    <td>{materielSurLeTerrain.serie1}</td>
                    <td>
                      <Translate contentKey={`stockageApp.Etat.${materielSurLeTerrain.etat1}`} />
                    </td>
                    <td>{materielSurLeTerrain.nombre1}</td>
                    <td>
                      <Translate contentKey={`stockageApp.Models.${materielSurLeTerrain.materiel2}`} />
                    </td>
                    <td>{materielSurLeTerrain.serie2}</td>
                    <td>
                      <Translate contentKey={`stockageApp.Etat.${materielSurLeTerrain.etat2}`} />
                    </td>
                    <td>{materielSurLeTerrain.nombre2}</td>
                    <td>
                      <Translate contentKey={`stockageApp.Models.${materielSurLeTerrain.materiel3}`} />
                    </td>
                    <td>{materielSurLeTerrain.serie3}</td>
                    <td>
                      <Translate contentKey={`stockageApp.Etat.${materielSurLeTerrain.etat3}`} />
                    </td>
                    <td>{materielSurLeTerrain.nombre3}</td>
                    <td>
                      <Translate contentKey={`stockageApp.Models.${materielSurLeTerrain.materiel4}`} />
                    </td>
                    <td>{materielSurLeTerrain.serie4}</td>
                    <td>
                      <Translate contentKey={`stockageApp.Etat.${materielSurLeTerrain.etat4}`} />
                    </td>
                    <td>{materielSurLeTerrain.nombre4}</td>
                    <td>
                      {materielSurLeTerrain.equipe ? (
                        <Link to={`/equipe/${materielSurLeTerrain.equipe.id}`}>{materielSurLeTerrain.equipe.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          as={Link as any}
                          to={`/materiel-sur-le-terrain/${materielSurLeTerrain.id}`}
                          variant="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          as={Link as any}
                          to={`/materiel-sur-le-terrain/${materielSurLeTerrain.id}/edit`}
                          variant="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/materiel-sur-le-terrain/${materielSurLeTerrain.id}/delete`)}
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
                <Translate contentKey="stockageApp.materielSurLeTerrain.home.notFound">No Materiel Sur Le Terrains found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default MaterielSurLeTerrain;
