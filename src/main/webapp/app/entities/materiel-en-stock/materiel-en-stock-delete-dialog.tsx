import React, { useEffect, useState } from 'react';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'react-bootstrap';
import { Translate } from 'react-jhipster';
import { useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { deleteEntity, getEntity } from './materiel-en-stock.reducer';

export const MaterielEnStockDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const materielEnStockEntity = useAppSelector(state => state.materielEnStock.entity);
  const updateSuccess = useAppSelector(state => state.materielEnStock.updateSuccess);

  const handleClose = () => {
    navigate('/materiel-en-stock');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(materielEnStockEntity.id));
  };

  return (
    <Modal show onHide={handleClose}>
      <ModalHeader data-cy="materielEnStockDeleteDialogHeading" closeButton>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="stockageApp.materielEnStock.delete.question">
        <Translate contentKey="stockageApp.materielEnStock.delete.question" interpolate={{ id: materielEnStockEntity.id }}>
          Are you sure you want to delete this MaterielEnStock?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button variant="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-materielEnStock" data-cy="entityConfirmDeleteButton" variant="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default MaterielEnStockDeleteDialog;
