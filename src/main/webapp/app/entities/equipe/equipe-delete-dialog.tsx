import React, { useEffect, useState } from 'react';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'react-bootstrap';
import { Translate } from 'react-jhipster';
import { useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { deleteEntity, getEntity } from './equipe.reducer';

export const EquipeDeleteDialog = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const equipeEntity = useAppSelector(state => state.equipe.entity);
  const updateSuccess = useAppSelector(state => state.equipe.updateSuccess);

  const handleClose = () => {
    navigate('/equipe');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(equipeEntity.id));
  };

  return (
    <Modal show onHide={handleClose}>
      <ModalHeader data-cy="equipeDeleteDialogHeading" closeButton>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="stockageApp.equipe.delete.question">
        <Translate contentKey="stockageApp.equipe.delete.question" interpolate={{ id: equipeEntity.id }}>
          Are you sure you want to delete this Equipe?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button variant="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-equipe" data-cy="entityConfirmDeleteButton" variant="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default EquipeDeleteDialog;
